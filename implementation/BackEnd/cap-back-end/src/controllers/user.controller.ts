// Copyright IBM Corp. 2020. All Rights Reserved.
// Node module: @loopback/example-todo-jwt
// This file is licensed under the MIT License.
// License text available at https://opensource.org/licenses/MIT

import { authenticate, TokenService } from "@loopback/authentication"
import { TokenServiceBindings } from "@loopback/authentication-jwt"
import { inject } from "@loopback/core"
import { repository } from "@loopback/repository"
import {
	get,
	getModelSchemaRef,
	HttpErrors,
	param,
	post,
	requestBody,
	SchemaObject,
} from "@loopback/rest"
import { SecurityBindings, securityId, UserProfile } from "@loopback/security"
import { compare, genSalt, hash } from "bcryptjs"
import _ from "lodash"

import { UserRepository } from "../repositories"
import { User } from "../models"
import { FirebaseCloudMessaging } from "../firebase-cloud-messaging"

const CredentialsSchema: SchemaObject = {
	type: "object",
	required: ["email", "password"],
	properties: {
		email: {
			type: "string",
			format: "email",
		},
		password: {
			type: "string",
			minLength: 8,
		},
	},
}

export type Credentials = {
	email: string
	password: string
}

export const CredentialsRequestBody = {
	description: "The input of login function",
	required: true,
	content: {
		"application/json": { schema: CredentialsSchema },
	},
}

const LocationSchema: SchemaObject = {
	type: "object",
	required: ["lat", "lng"],
	properties: {
		lat: {
			type: "number",
		},
		lng: {
			type: "number",
		},
	},
}

export type Location = {
	lat: Number
	lng: Number
}

export type UserLocation = Location & {
	uid: string
}

export type UserDetails = {
	uid: string
	bio: string
	avatar: string
	firstname: string
	lastname: string
	gender: string
	birthdate: Date
}

export const LocationRequestBody = {
	description: "The input of setLocation function",
	required: true,
	content: {
		"application/json": { schema: LocationSchema },
	},
}

export async function getCurrentUser(
	user: UserProfile,
	userRepository: UserRepository
) {
	const uid = user[securityId]

	const currentUser = await userRepository.findOne({
		where: { securityId: uid },
	})

	if (!currentUser) {
		throw new HttpErrors.Unauthorized("Invalid User")
	}

	return currentUser
}

export async function getUserByUID(
	uid: string,
	userRepository: UserRepository
) {
	const user = await userRepository.findOne({
		where: { securityId: uid },
	})

	if (!user) {
		throw new HttpErrors.Unauthorized("Invalid User")
	}

	return user
}

export class UserController {
	constructor(
		@inject(TokenServiceBindings.TOKEN_SERVICE)
		public jwtService: TokenService,
		@inject(SecurityBindings.USER, { optional: true })
		public user: UserProfile,
		@repository(UserRepository)
		protected userRepository: UserRepository
	) {}

	@post("/login", {
		responses: {
			"200": {
				description: "Token",
				content: {
					"application/json": {
						schema: {
							type: "object",
							properties: {
								token: {
									type: "string",
								},
								uid: {
									type: "string",
								},
							},
						},
					},
				},
			},
		},
	})
	async login(
		@requestBody(CredentialsRequestBody) credentials: Credentials
	): Promise<{ token: string; uid: string }> {
		const invalidCredentialsError = "Invalid email or password."

		const foundUser = await this.userRepository.findOne({
			where: { email: credentials.email },
		})

		if (!foundUser) {
			throw new HttpErrors.Unauthorized(invalidCredentialsError)
		}

		const passwordMatched = await compare(
			credentials.password,
			foundUser.password
		)

		if (!passwordMatched) {
			throw new HttpErrors.Unauthorized(invalidCredentialsError)
		}

		const userProfile = {
			[securityId]: foundUser.securityId,
			id: foundUser.id,
			email: foundUser.email,
		}

		const token = await this.jwtService.generateToken(userProfile)

		return { token, uid: foundUser.securityId }
	}

	@authenticate("jwt")
	@get("/who-am-i", {
		responses: {
			"200": {
				description: "Return current user",
				content: {
					"application/json": {
						schema: {
							type: "string",
						},
					},
				},
			},
		},
	})
	async whoAmI(): Promise<string> {
		return this.user[securityId]
	}

	@authenticate("jwt")
	@get("/discover")
	async discoverUsers(): Promise<UserLocation[]> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)
		const users = await this.userRepository.find({
			where: { id: { neq: currentUser.id } },
		})

		return users.map((user: User) => ({
			uid: user.securityId,
			lat: user.lat,
			lng: user.lng,
			gender: user.gender,
		}))
	}

	@authenticate("jwt")
	@post("/location", {
		responses: {
			"204": {
				description: "Update the location of the requesting user",
			},
		},
	})
	async setLocation(
		@requestBody(LocationRequestBody) location: Location
	): Promise<void> {
		const currentUser = await getCurrentUser(this.user, this.userRepository)

		currentUser.lat = location.lat
		currentUser.lng = location.lng

		await this.userRepository.update(currentUser)
	}

	@post("/signup", {
		responses: {
			"200": {
				description: "User",
				content: {
					"application/json": {
						schema: {
							"x-ts-type": User,
						},
					},
				},
			},
		},
	})
	async signUp(
		@requestBody({
			content: {
				"application/json": {
					schema: getModelSchemaRef(User, {
						exclude: [
							"id",
							"securityId",
							"createdAt",
							"visibility",
						],
						title: "NewUser",
					}),
				},
			},
		})
		newUserRequest: Omit<
			User,
			"id" | "securityId" | "createdAt" | "visibility"
		>
	): Promise<User> {
		const password = await hash(newUserRequest.password, await genSalt())
		const savedUser = await this.userRepository.create({
			..._.omit(newUserRequest, "password"),
			password,
		})

		//await this.userRepository.userCredentials(savedUser.id).create({password});

		return savedUser
	}

	@get("/check-email/{email}", {
		responses: {
			"200": {
				description:
					"Returns true if email is available, false if already in use",
			},
		},
	})
	async checkMail(
		@param.path.string("email") email: string
	): Promise<Boolean> {
		const foundUser = await this.userRepository.findOne({
			where: { email },
		})

		return foundUser === null
	}

	@authenticate("jwt")
	@get("/user-details/{uid}")
	async findByUID(
		@param.path.string("uid") uid: string
	): Promise<UserDetails> {
		const user = await this.userRepository.findOne({
			where: { securityId: uid },
		})

		if (user === null) {
			throw new HttpErrors.NotFound("user not found")
		}

		return {
			uid: user.securityId,
			bio: user.bio,
			avatar: user.avatar,
			firstname: user.firstName,
			lastname: user.lastName,
			gender: user.gender,
			birthdate: user.birthDate,
		}
	}
}
