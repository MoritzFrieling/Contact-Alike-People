import { inject, Getter } from "@loopback/core"
import {
	DefaultCrudRepository,
	repository,
	BelongsToAccessor,
} from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { Interest, InterestRelations, InterestType } from "../models"
import { InterestTypeRepository } from "./interest-type.repository"

export class InterestRepository extends DefaultCrudRepository<
	Interest,
	typeof Interest.prototype.id,
	InterestRelations
> {
	public readonly interestType: BelongsToAccessor<
		InterestType,
		typeof Interest.prototype.id
	>

	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
		@repository.getter("InterestTypeRepository")
		protected interestTypeRepositoryGetter: Getter<InterestTypeRepository>
	) {
		super(Interest, dataSource)
		this.interestType = this.createBelongsToAccessorFor(
			"interestType",
			interestTypeRepositoryGetter
		)
		this.registerInclusionResolver(
			"interestType",
			this.interestType.inclusionResolver
		)
	}
}
