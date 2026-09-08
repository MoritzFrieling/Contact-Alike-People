import { inject } from "@loopback/core"
import { DefaultCrudRepository } from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { InterestType, InterestTypeRelations } from "../models"

export class InterestTypeRepository extends DefaultCrudRepository<
	InterestType,
	typeof InterestType.prototype.id,
	InterestTypeRelations
> {
	constructor(@inject("datasources.MariaDB") dataSource: MariaDbDataSource) {
		super(InterestType, dataSource)
	}
}
