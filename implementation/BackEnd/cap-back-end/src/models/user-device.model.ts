import { Entity, model, property, belongsTo } from "@loopback/repository"
import { User } from "./user.model"

@model({
	settings: {
		strict: true,
		foreignKeys: {
			// eslint-disable-next-line @typescript-eslint/naming-convention
			fk_user: {
				name: "fk_user",
				entity: "User",
				entityKey: "id",
				foreignKey: "userId",
			},
		},
	},
})
export class UserDevice extends Entity {
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
	deviceId: string

	constructor(data?: Partial<UserDevice>) {
		super(data)
	}
}

export interface UserDeviceRelations {
	// describe navigational properties here
}

export type UserDeviceWithRelations = UserDevice & UserDeviceRelations;
