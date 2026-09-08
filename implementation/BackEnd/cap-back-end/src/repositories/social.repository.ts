import { inject } from "@loopback/core"
import { DefaultCrudRepository } from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { Social, SocialRelations } from "../models"

export class SocialRepository extends DefaultCrudRepository<
	Social,
	typeof Social.prototype.id,
	SocialRelations
> {
	constructor(@inject("datasources.MariaDB") dataSource: MariaDbDataSource) {
		super(Social, dataSource)
	}
}
