import { inject, Getter } from "@loopback/core"
import {
	DefaultCrudRepository,
	repository,
	BelongsToAccessor,
} from "@loopback/repository"
import { MariaDbDataSource } from "../datasources"
import { UserSocial, UserSocialRelations, User, Social } from "../models"
import { UserRepository } from "./user.repository"
import { SocialRepository } from "./social.repository"

export class UserSocialRepository extends DefaultCrudRepository<
	UserSocial,
	typeof UserSocial.prototype.id,
	UserSocialRelations
> {
	public readonly user: BelongsToAccessor<
		User,
		typeof UserSocial.prototype.id
	>

	public readonly social: BelongsToAccessor<
		Social,
		typeof UserSocial.prototype.id
	>

	constructor(
		@inject("datasources.MariaDB") dataSource: MariaDbDataSource,
		@repository.getter("UserRepository")
		protected userRepositoryGetter: Getter<UserRepository>,
		@repository.getter("SocialRepository")
		protected socialRepositoryGetter: Getter<SocialRepository>
	) {
		super(UserSocial, dataSource)
		this.social = this.createBelongsToAccessorFor(
			"social",
			socialRepositoryGetter
		)
		this.registerInclusionResolver("social", this.social.inclusionResolver)
		this.user = this.createBelongsToAccessorFor(
			"user",
			userRepositoryGetter
		)
		this.registerInclusionResolver("user", this.user.inclusionResolver)
	}
}
