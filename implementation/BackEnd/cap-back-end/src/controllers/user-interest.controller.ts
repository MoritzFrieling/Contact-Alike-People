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
import { UserInterest } from "../models"
import { UserInterestRepository, UserRepository } from "../repositories"
import { authenticate } from "@loopback/authentication"
import { inject } from "@loopback/core"
import { SecurityBindings, UserProfile } from "@loopback/security"
import { getCurrentUser } from "./user.controller"

export class UserInterestController {
	constructor(
		@inject(SecurityBindings.USER)
		public user: UserProfile,
		@repository(UserRepository)
		protected userRepository: UserRepository,
		@repository(UserInterestRepository)
		public userInterestRepository: UserInterestRepository
	) {}

	@authenticate("jwt")
	@post("/user-interests")
	@response(200, {
		description: "UserInterest model instance",
		content: {
			"application/json": { schema: getModelSchemaRef(UserInterest) },
		},
	})
	async create(
		@requestBody({
			content: {
				"application/json": {
					schema: getModelSchemaRef(UserInterest, {
						title: "NewUserInterest",
						exclude: ["id", "userId"],
					}),
				},
			},
		})
		userInterest: Omit<UserInterest, "id" | "userId">
	): Promise<UserInterest> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		const newUserInterest = {
			...userInterest,
			userId: currentUser.id,
		}

		return this.userInterestRepository.create(newUserInterest)
	}

	@authenticate("jwt")
	@get("/user-interests")
	@response(200, {
		description: "Array of UserInterest model instances",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(UserInterest, {
						includeRelations: true,
					}),
				},
			},
		},
	})
	async find(): Promise<UserInterest[]> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		return this.userInterestRepository.find({
			where: { userId: currentUser.id },
		})
	}

	@authenticate("jwt")
	@get("/user-interests-by-user/{uid}")
	@response(200, {
		description: "Array of UserInterest model instances for the given user",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(UserInterest, {
						includeRelations: true,
					}),
				},
			},
		},
	})
	async findByUID(
		@param.path.string("uid") uid: string
	): Promise<UserInterest[]> {
		const user = await this.userRepository.findOne({
			where: { securityId: uid },
		})

		if (user === null) {
			throw new HttpErrors.NotFound("user not found")
		}

		return this.userInterestRepository.find({ where: { userId: user.id } })
	}

	/*@authenticate("jwt")
	@get("/user-interests/{id}")
	@response(200, {
		description: "UserInterest model instance",
		content: {
			"application/json": {
				schema: getModelSchemaRef(UserInterest, { includeRelations: true }),
			},
		},
	})
	async findById(
		@param.path.number("id") id: number,
		@param.filter(UserInterest, { exclude: "where" }) filter?: FilterExcludingWhere<UserInterest>,
	): Promise<UserInterest> {
		return this.userInterestRepository.findById(id, filter)
	}*/

	@authenticate("jwt")
	@patch("/user-interests/{id}")
	@response(204, {
		description: "UserInterest PATCH success",
	})
	async updateById(
		@param.path.number("id") id: number,
		@requestBody({
			content: {
				"application/json": {
					schema: getModelSchemaRef(UserInterest, { partial: true }),
				},
			},
		})
		userInterest: UserInterest
	): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const foundInterest = await this.userInterestRepository.findOne({
			where: { id, userId: currentUser.id },
		})

		if (!foundInterest) {
			throw new HttpErrors.NotFound("Unknown Interest Id")
		}

		return this.userInterestRepository.update(foundInterest, userInterest)
	}

	@authenticate("jwt")
	@del("/user-interests/{id}")
	@response(204, {
		description: "UserInterest DELETE success",
	})
	async deleteById(@param.path.number("id") id: number): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const foundInterest = await this.userInterestRepository.findOne({
			where: { id, userId: currentUser.id },
		})

		if (!foundInterest) {
			throw new HttpErrors.NotFound("Unknown Interest Id")
		}

		return this.userInterestRepository.delete(foundInterest)
	}
}
