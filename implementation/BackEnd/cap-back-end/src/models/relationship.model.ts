import { Entity, model, property } from "@loopback/repository"

@model({
	settings: {
		strict: true,
		foreignKeys: {
			// eslint-disable-next-line @typescript-eslint/naming-convention
			fk_fromUser: {
				name: "fk_fromUser",
				entity: "User",
				entityKey: "id",
				foreignKey: "fromUser",
			},
			fk_toUser: {
				name: "fk_toUser",
				entity: "User",
				entityKey: "id",
				foreignKey: "toUser",
			},
		},
	},
})
export class Relationship extends Entity {
	@property({
		type: "number",
		id: true,
		generated: true,
	})
	id?: number

	@property({
		type: "number",
		required: true,
	})
	fromUser: number

	@property({
		type: "number",
		required: true,
	})
	toUser: number

	@property({
		type: "string",
	})
	state?: string


	constructor(data?: Partial<Relationship>) {
		super(data)
	}
}

export interface RelationshipRelations {
	// describe navigational properties here
}

export type RelationshipWithRelations = Relationship & RelationshipRelations;
