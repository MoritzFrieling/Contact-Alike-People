import { Entity, model, property, belongsTo } from "@loopback/repository"
import { User } from "./user.model"
import { Interest } from "./interest.model"

@model({
	settings: {
		strict: true,
		foreignKeys: {
			// eslint-disable-next-line @typescript-eslint/naming-convention
			fk_userInterest_userId: {
				name: "fk_userInterest_userId",
				entity: "User",
				entityKey: "id",
				foreignKey: "userId",
			},
			fk_userInterest_interestId: {
				name: "fk_userInterest_interestId",
				entity: "Interest",
				entityKey: "id",
				foreignKey: "interestId",
			},
		},
	},
})
export class UserInterest extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@belongsTo(() => User)
	userId: number

	@belongsTo(() => Interest)
	interestId: number

	@property({
		type: "string",
	})
	description: string

	@property({
		type: "date",
	})
	startDate?: string

	@property({
		type: "number",
	})
	proficiency?: number;

	constructor(data?: Partial<UserInterest>) {
		super(data)
	}
}

export interface UserInterestRelations {
	// describe navigational properties here
}

export type UserInterestWithRelations = UserInterest & UserInterestRelations
