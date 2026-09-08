import { inject } from "@loopback/core"
import { DefaultCrudRepository } from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { Relationship, RelationshipRelations } from "../models"

export class RelationshipRepository extends DefaultCrudRepository<Relationship,
	typeof Relationship.prototype.id,
	RelationshipRelations> {
	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
	) {
		super(Relationship, dataSource)
	}
}
