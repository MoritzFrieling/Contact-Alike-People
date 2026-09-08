import { Entity, model, property, belongsTo } from "@loopback/repository"
import { User } from "./user.model"
import { Social } from "./social.model"

@model({
	settings: {
		strict: true,
		foreignKeys: {
			// eslint-disable-next-line @typescript-eslint/naming-convention
			fk_userSocial_userId: {
				name: "fk_userSocial_userId",
				entity: "User",
				entityKey: "id",
				foreignKey: "userId",
			},
			fk_userSocial_socialId: {
				name: "fk_userSocial_socialId",
				entity: "Social",
				entityKey: "id",
				foreignKey: "socialId",
			},
		},
	},
})
export class UserSocial extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@belongsTo(() => User)
	userId: number

	@belongsTo(() => Social)
	socialId: number

	@property({
		type: "string",
		required: true,
	})
	handle: string;

	constructor(data?: Partial<UserSocial>) {
		super(data)
	}
}

export interface UserSocialRelations {
	// describe navigational properties here
}

export type UserSocialWithRelations = UserSocial & UserSocialRelations
