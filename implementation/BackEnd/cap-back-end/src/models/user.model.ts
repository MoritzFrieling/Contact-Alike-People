import { Entity, model, property, hasMany, hasOne } from "@loopback/repository"
import { UserInterest } from "./user-interest.model"
import { UserSocial } from "./user-social.model"
import { UserAddress } from "./user-address.model"
import { UserDevice } from "./user-device.model"

@model({
	settings: {
		strict: true,
	},
})
export class User extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@property({
		type: "string",
		generated: false,
		defaultFn: "uuidv4",
	})
	securityId: string

	@property({
		type: "string",
		required: true,
	})
	firstName: string

	@property({
		type: "string",
		required: true,
	})
	lastName: string

	@property({
		type: "string",
		required: true,
	})
	password: string

	@property({
		type: "string",
		required: true,
	})
	email: string

	@property({
		type: "date",
		dataType: "timestamp",
		defaultFn: "now",
	})
	createdAt: Date

	@property({
		type: "string",
		required: true,
	})
	bio: string

	@property({
		type: "string",
		required: true,
		dataType: "mediumtext",
	})
	avatar: string

	@property({
		type: "string",
	})
	phoneNumber: string

	@property({
		type: "date",
		jsonSchema: {
			format: "date", // This can be changed to 'date-time', 'time' or 'date'
		},
	})
	birthDate: Date

	@property({
		type: "string",
	})
	gender: string

	@property({
		type: "boolean",
		default: true,
	})
	visibility: boolean

	@property({
		type: "number",
		dataType: "FLOAT",
	})
	lat: Number

	@property({
		type: "number",
		dataType: "FLOAT",
	})
	lng: Number

	@hasMany(() => UserInterest)
	userInterests: UserInterest[]

	@hasMany(() => UserSocial)
	userSocials: UserSocial[]

	@hasOne(() => UserAddress)
	userAddress: UserAddress

	@hasMany(() => UserDevice)
	userDevices: UserDevice[]

	constructor(data?: Partial<User>) {
		super(data)
	}
}

export interface UserRelations {
	// describe navigational properties here
}

export type UserWithRelations = User & UserRelations
