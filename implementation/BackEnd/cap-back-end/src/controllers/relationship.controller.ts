import { repository } from "@loopback/repository"
import { param, put, get, post, HttpErrors } from "@loopback/rest"
import { Relationship } from "../models"
import {
	RelationshipRepository,
	UserDeviceRepository,
	UserRepository,
} from "../repositories"
import { inject } from "@loopback/core"
import { SecurityBindings, UserProfile } from "@loopback/security"
import { authenticate } from "@loopback/authentication"
import { getCurrentUser, getUserByUID } from "./user.controller"
import {
	CloudMessage,
	FirebaseCloudMessaging,
} from "../firebase-cloud-messaging"

export class RelationshipController {
	constructor(
		@inject(SecurityBindings.USER)
		public user: UserProfile,
		@repository(UserRepository)
		protected userRepository: UserRepository,
		@repository(RelationshipRepository)
		public relationshipRepository: RelationshipRepository,
		@repository(UserDeviceRepository)
		public userDeviceRepository: UserDeviceRepository
	) {}

	@authenticate("jwt")
	@get("/relationships")
	async getRelationsShips() {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		return this.relationshipRepository.find({
			where: {
				or: [{ fromUser: currentUser.id }, { toUser: currentUser.id }],
			},
		})
	}

	@authenticate("jwt")
	@get("/relationships/out")
	async getOutgoingRequests() {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		return this.relationshipRepository.find({
			where: {
				or: [{ fromUser: currentUser.id }],
			},
		})
	}

	@authenticate("jwt")
	@get("/relationships/details")
	async getIncomingRequestsEx() {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		const rel = await this.relationshipRepository.find({
			where: {
				toUser: currentUser.id,
			},
		})

		const inRelationShipsDetailed = []

		for (const r of rel) {
			const fromUser = await this.userRepository.findById(r.fromUser)

			inRelationShipsDetailed.push({
				fromUser: fromUser.securityId,
				accepted: r.state === "ACCEPTED",
			})
		}

		return inRelationShipsDetailed
	}

	@authenticate("jwt")
	@get("/relationships/in")
	async getIncomingRequests() {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		return this.relationshipRepository.find({
			where: {
				toUser: currentUser.id,
			},
		})
	}

	@authenticate("jwt")
	@post("/relationships/{uid}")
	async addRelationship(
		@param.path.string("uid") uid: string
	): Promise<Relationship> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const targetUser = await getUserByUID(uid, this.userRepository)

		if (currentUser.id === targetUser.id) {
			throw new HttpErrors.Forbidden("You are your best friend")
		}

		const existingRelationship = await this.relationshipRepository.findOne({
			where: {
				or: [
					{
						and: [
							{ fromUser: currentUser.id },
							{ toUser: targetUser.id },
						]
					},
					{
						and: [
							{ fromUser: targetUser.id },
							{ toUser: currentUser.id },
						]
					},
				],
			},
		})

		if (existingRelationship !== null) {
			throw new HttpErrors.Forbidden("Relationship exists")
		}

		const newRelationship = await this.relationshipRepository.create({
			fromUser: currentUser.id,
			toUser: targetUser.id,
			state: "REQUEST",
		})

		const targetUserDevices = await this.userDeviceRepository.find({
			where: { userId: targetUser.id },
		})
		const targetIds = targetUserDevices.map(
			(userDevice) => userDevice.deviceId
		)

		console.debug("addRelationship | targetIds", targetIds)

		const message = {
			collapse_key: "collapse-key",
			data: {
				fromUserUID: currentUser.securityId,
			},
			notification: {
				title: "New Contact Request",
				body: `${currentUser.firstName} wants to contact you!`,
			},
		}

		FirebaseCloudMessaging.getInstance().sendMessage(message, targetIds)

		return newRelationship
	}

	@authenticate("jwt")
	@put("/relationships/{uid}")
	async updateRelationship(
		@param.path.string("uid") uid: string
	): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const sourceUser = await getUserByUID(uid, this.userRepository)

		if (currentUser.id === sourceUser.id) {
			throw new HttpErrors.Forbidden("You are your best friend")
		}

		const existingRelationship = await this.relationshipRepository.findOne({
			where: {
				or: [{ fromUser: sourceUser.id }, { toUser: currentUser.id }],
			},
		})

		if (existingRelationship === null) {
			throw new HttpErrors.Forbidden("Relationship does not exists")
		}

		existingRelationship.state = "ACCEPTED"

		await this.relationshipRepository.update(existingRelationship)

		const targetUserDevices = await this.userDeviceRepository.find({
			where: { userId: sourceUser.id },
		})
		const targetIds = targetUserDevices.map(
			(userDevice) => userDevice.deviceId
		)

		console.debug("updateRelationship | targetIds", targetIds)

		const message = {
			collapse_key: "collapse-key",
			data: {
				fromUserUID: sourceUser.securityId,
			},
			notification: {
				title: "Request Accepted",
				body: `${currentUser.firstName} accepted your request!`,
			},
		}

		FirebaseCloudMessaging.getInstance().sendMessage(message, targetIds)
	}
}
