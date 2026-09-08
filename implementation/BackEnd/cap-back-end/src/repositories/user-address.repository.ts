import { inject, Getter } from "@loopback/core"
import {
	DefaultCrudRepository,
	repository,
	BelongsToAccessor,
} from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { UserAddress, UserAddressRelations, User } from "../models"
import { UserRepository } from "./user.repository"

export class UserAddressRepository extends DefaultCrudRepository<
	UserAddress,
	typeof UserAddress.prototype.id,
	UserAddressRelations
> {
	public readonly user: BelongsToAccessor<
		User,
		typeof UserAddress.prototype.id
	>

	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
		@repository.getter("UserRepository")
		protected userRepositoryGetter: Getter<UserRepository>
	) {
		super(UserAddress, dataSource)
		this.user = this.createBelongsToAccessorFor(
			"user",
			userRepositoryGetter
		)
		this.registerInclusionResolver("user", this.user.inclusionResolver)
	}
}
