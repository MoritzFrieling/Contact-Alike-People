import { inject, Getter } from "@loopback/core"
import {
	DefaultCrudRepository,
	repository,
	HasManyRepositoryFactory,
	HasOneRepositoryFactory,
} from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import {
	User,
	UserRelations,
	UserInterest,
	UserSocial,
	UserAddress, UserDevice,
} from "../models"
import { UserInterestRepository } from "./user-interest.repository"
import { UserSocialRepository } from "./user-social.repository"
import { UserAddressRepository } from "./user-address.repository"
import { UserDeviceRepository } from "./user-device.repository"

export class UserRepository extends DefaultCrudRepository<User,
	typeof User.prototype.id,
	UserRelations> {
	public readonly userInterests: HasManyRepositoryFactory<UserInterest,
		typeof User.prototype.id>

	public readonly userSocials: HasManyRepositoryFactory<UserSocial,
		typeof User.prototype.id>

	public readonly userAddress: HasOneRepositoryFactory<UserAddress,
		typeof User.prototype.id>

	public readonly userDevices: HasManyRepositoryFactory<UserDevice, typeof User.prototype.id>

	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
		@repository.getter("UserInterestRepository")
		protected userInterestRepositoryGetter: Getter<UserInterestRepository>,
		@repository.getter("UserSocialRepository")
		protected userSocialRepositoryGetter: Getter<UserSocialRepository>,
		@repository.getter("UserAddressRepository")
		protected userAddressRepositoryGetter: Getter<UserAddressRepository>,
		@repository.getter("UserDeviceRepository")
		protected userDeviceRepositoryGetter: Getter<UserDeviceRepository>,
	) {
		super(User, dataSource)
		this.userDevices = this.createHasManyRepositoryFactoryFor("userDevices", userDeviceRepositoryGetter)
		this.registerInclusionResolver("userDevices", this.userDevices.inclusionResolver)
		this.userAddress = this.createHasOneRepositoryFactoryFor(
			"userAddress",
			userAddressRepositoryGetter,
		)
		this.registerInclusionResolver(
			"userAddress",
			this.userAddress.inclusionResolver,
		)
		this.userSocials = this.createHasManyRepositoryFactoryFor(
			"userSocials",
			userSocialRepositoryGetter,
		)
		this.registerInclusionResolver(
			"userSocials",
			this.userSocials.inclusionResolver,
		)
		this.userInterests = this.createHasManyRepositoryFactoryFor(
			"userInterests",
			userInterestRepositoryGetter,
		)
		this.registerInclusionResolver(
			"userInterests",
			this.userInterests.inclusionResolver,
		)
	}
}
