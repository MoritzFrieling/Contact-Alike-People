import { Entity, model, property, hasMany } from "@loopback/repository"
import { Interest } from "./interest.model"

@model({ settings: { strict: true } })
export class InterestType extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@property({
		type: "string",
		required: true,
	})
	name: string;

	constructor(data?: Partial<InterestType>) {
		super(data)
	}
}

export interface InterestTypeRelations {
	// describe navigational properties here
}

export type InterestTypeWithRelations = InterestType & InterestTypeRelations
