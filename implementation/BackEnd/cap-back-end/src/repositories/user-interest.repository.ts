import { inject, Getter } from "@loopback/core"
import {
	DefaultCrudRepository,
	repository,
	BelongsToAccessor,
} from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { UserInterest, UserInterestRelations, Interest } from "../models"
import { InterestRepository } from "./interest.repository"

export class UserInterestRepository extends DefaultCrudRepository<
	UserInterest,
	typeof UserInterest.prototype.id,
	UserInterestRelations
> {
	public readonly interest: BelongsToAccessor<
		Interest,
		typeof UserInterest.prototype.id
	>

	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
		@repository.getter("InterestRepository")
		protected interestRepositoryGetter: Getter<InterestRepository>
	) {
		super(UserInterest, dataSource)
		this.interest = this.createBelongsToAccessorFor(
			"interest",
			interestRepositoryGetter
		)
		this.registerInclusionResolver(
			"interest",
			this.interest.inclusionResolver
		)
	}
}
