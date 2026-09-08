import { Entity, model, property, belongsTo } from "@loopback/repository"
import { InterestType } from "./interest-type.model"

@model({
	settings: {
		strict: true,
		foreignKeys: {
			// eslint-disable-next-line @typescript-eslint/naming-convention
			fk_interest_interestTypeId: {
				name: "fk_interest_interestTypeId",
				entity: "InterestType",
				entityKey: "id",
				foreignKey: "interestTypeId",
			},
		},
	},
})
export class Interest extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@belongsTo(() => InterestType)
	interestTypeId: number

	@property({
		type: "string",
		required: true,
	})
	name: string

	@property({
		type: "string",
		required: true,
	})
	description: string;

	constructor(data?: Partial<Interest>) {
		super(data)
	}
}

export interface InterestRelations {
	// describe navigational properties here
}

export type InterestWithRelations = Interest & InterestRelations
