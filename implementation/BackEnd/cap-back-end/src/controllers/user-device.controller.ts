import {
	repository,
} from "@loopback/repository"
import {
	put,
	param,
	get,
	getModelSchemaRef,
	del,
	response, HttpErrors,
} from "@loopback/rest"
import { UserDevice } from "../models"
import { UserDeviceRepository, UserRepository } from "../repositories"
import { inject } from "@loopback/core"
import { SecurityBindings, UserProfile } from "@loopback/security"
import { authenticate } from "@loopback/authentication"
import { getCurrentUser } from "./user.controller"

export class UserDeviceController {
	constructor(
		@inject(SecurityBindings.USER)
		public user: UserProfile,
		@repository(UserRepository)
		protected userRepository: UserRepository,
		@repository(UserDeviceRepository)
		public userDeviceRepository: UserDeviceRepository,
	) {
	}

	@authenticate("jwt")
	@put("/user-devices/{deviceId}")
	@response(204, {
		description: "UserDevice PUT success",
	})
	async tryCreate(
		@param.path.string("deviceId") deviceId: string,
	): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const existingDevice = await this.userDeviceRepository.findOne({
			where: { userId: currentUser.id, deviceId },
		})

		if (existingDevice !== null) {
			return
		}

		const newUserDevice = {
			deviceId,
			userId: currentUser.id,
		}

		await this.userDeviceRepository.create(newUserDevice)
	}

	@authenticate("jwt")
	@get("/user-devices")
	@response(200, {
		description: "Array of UserDevice model instances",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(UserDevice, { includeRelations: true }),
				},
			},
		},
	})
	async find(): Promise<UserDevice[]> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		return this.userDeviceRepository.find({
			where: { userId: currentUser.id },
		})
	}

	@authenticate("jwt")
	@del("/user-devices/{id}")
	@response(204, {
		description: "UserDevice DELETE success",
	})
	async deleteById(@param.path.number("id") id: number): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const foundInterest = await this.userDeviceRepository.findOne({
			where: { id, userId: currentUser.id },
		})

		if (!foundInterest) {
			throw new HttpErrors.NotFound("Unknown Device Id")
		}

		return this.userDeviceRepository.delete(foundInterest)
	}
}
