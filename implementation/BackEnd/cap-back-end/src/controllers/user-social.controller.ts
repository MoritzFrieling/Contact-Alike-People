import { repository } from "@loopback/repository"
import {
	post,
	param,
	get,
	getModelSchemaRef,
	patch,
	del,
	requestBody,
	response,
	HttpErrors,
} from "@loopback/rest"
import { UserSocial } from "../models"
import { UserRepository, UserSocialRepository } from "../repositories"
import { inject } from "@loopback/core"
import { authenticate } from "@loopback/authentication"
import { SecurityBindings, UserProfile } from "@loopback/security"
import { getCurrentUser } from "./user.controller"

export class UserSocialController {
	constructor(
		@inject(SecurityBindings.USER)
		public user: UserProfile,
		@repository(UserRepository)
		protected userRepository: UserRepository,
		@repository(UserSocialRepository)
		public userSocialRepository: UserSocialRepository
	) {}

	@authenticate("jwt")
	@post("/user-socials")
	@response(200, {
		description: "UserSocial model instance",
		content: {
			"application/json": { schema: getModelSchemaRef(UserSocial) },
		},
	})
	async create(
		@requestBody({
			content: {
				"application/json": {
					schema: getModelSchemaRef(UserSocial, {
						title: "NewUserSocial",
						exclude: ["id", "userId"],
					}),
				},
			},
		})
		userSocial: Omit<UserSocial, "id" | "userId">
	): Promise<UserSocial> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		const newUserSocial = {
			...userSocial,
			userId: currentUser.id,
		}

		return this.userSocialRepository.create(newUserSocial)
	}

	@authenticate("jwt")
	@get("/user-socials")
	@response(200, {
		description: "Array of UserSocial model instances",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(UserSocial, {
						includeRelations: true,
					}),
				},
			},
		},
	})
	async find(): Promise<UserSocial[]> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		return this.userSocialRepository.find({
			where: { userId: currentUser.id },
		})
	}

	@authenticate("jwt")
	@get("/user-socials-by-user/{uid}")
	@response(200, {
		description: "Array of UserSocial model instances for the given user",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(UserSocial, {
						includeRelations: true,
					}),
				},
			},
		},
	})
	async findByUID(
		@param.path.string("uid") uid: string
	): Promise<UserSocial[]> {
		const user = await this.userRepository.findOne({
			where: { securityId: uid },
		})

		if (user === null) {
			throw new HttpErrors.NotFound("user not found")
		}

		return this.userSocialRepository.find({ where: { userId: user.id } })
	}

	/*@authenticate("jwt")
	@get("/user-socials/{id}")
	@response(200, {
		description: "UserSocial model instance",
		content: {
			"application/json": {
				schema: getModelSchemaRef(UserSocial, { includeRelations: true }),
			},
		},
	})
	async findById(
		@param.path.number("id") id: number,
		@param.filter(UserSocial, { exclude: "where" }) filter?: FilterExcludingWhere<UserSocial>,
	): Promise<UserSocial> {
		return this.userSocialRepository.findById(id, filter)
	}*/

	@authenticate("jwt")
	@patch("/user-socials/{id}")
	@response(204, {
		description: "UserSocial PATCH success",
	})
	async updateById(
		@param.path.number("id") id: number,
		@requestBody({
			content: {
				"application/json": {
					schema: getModelSchemaRef(UserSocial, { partial: true }),
				},
			},
		})
		userSocial: UserSocial
	): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const foundSocial = await this.userSocialRepository.findOne({
			where: { id, userId: currentUser.id },
		})

		if (!foundSocial) {
			throw new HttpErrors.NotFound("Unknown Social Id")
		}

		return this.userSocialRepository.update(foundSocial, userSocial)
	}

	@authenticate("jwt")
	@del("/user-socials/{id}")
	@response(204, {
		description: "UserSocial DELETE success",
	})
	async deleteById(@param.path.number("id") id: number): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const foundSocial = await this.userSocialRepository.findOne({
			where: { id, userId: currentUser.id },
		})

		if (!foundSocial) {
			throw new HttpErrors.NotFound("Unknown Social Id")
		}

		return this.userSocialRepository.delete(foundSocial)
	}
}
