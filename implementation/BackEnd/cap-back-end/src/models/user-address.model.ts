import { Entity, model, property, belongsTo } from "@loopback/repository"
import { User } from "./user.model"

@model({
	settings: {
		strict: true,
		foreignKeys: {
			// eslint-disable-next-line @typescript-eslint/naming-convention
			fk_userAddress_userId: {
				name: "fk_userAddress_userId",
				entity: "User",
				entityKey: "id",
				foreignKey: "userId",
			},
		},
	},
})
export class UserAddress extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@belongsTo(() => User)
	userId: number

	@property({
		type: "string",
		required: true,
	})
	street: string

	@property({
		type: "string",
		required: true,
	})
	city: string

	@property({
		type: "string",
		required: true,
	})
	country: string

	@property({
		type: "number",
		required: true,
	})
	zipCode: number

	@property({
		type: "number",
		required: true,
	})
	houseNumber: number;

	constructor(data?: Partial<UserAddress>) {
		super(data)
	}
}

export interface UserAddressRelations {
	// describe navigational properties here
}

export type UserAddressWithRelations = UserAddress & UserAddressRelations
