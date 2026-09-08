import { inject, Getter } from "@loopback/core"
import { DefaultCrudRepository, repository, BelongsToAccessor } from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { UserDevice, UserDeviceRelations, User } from "../models"
import { UserRepository } from "./user.repository"

export class UserDeviceRepository extends DefaultCrudRepository<UserDevice,
	typeof UserDevice.prototype.id,
	UserDeviceRelations> {

	public readonly user: BelongsToAccessor<User, typeof UserDevice.prototype.id>

	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
		@repository.getter("UserRepository")
		protected userRepositoryGetter: Getter<UserRepository>,
	) {
		super(UserDevice, dataSource)
		this.user = this.createBelongsToAccessorFor("user", userRepositoryGetter)
	}
}
