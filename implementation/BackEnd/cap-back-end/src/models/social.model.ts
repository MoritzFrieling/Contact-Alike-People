import { Entity, model, property } from "@loopback/repository"

@model({ settings: { strict: true } })
export class Social extends Entity {
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

	constructor(data?: Partial<Social>) {
		super(data)
	}
}

export interface SocialRelations {
	// describe navigational properties here
}

export type SocialWithRelations = Social & SocialRelations
