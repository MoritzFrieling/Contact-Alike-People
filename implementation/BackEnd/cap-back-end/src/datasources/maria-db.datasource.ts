import { inject, lifeCycleObserver, LifeCycleObserver } from "@loopback/core"
import { juggler } from "@loopback/repository"

// Keep connection values outside version control. The defaults are intentionally
// local and cannot access a shared or remote database.
const config = {
	name: "mariadb",
	connector: "mysql",
	host: process.env.DB_HOST ?? "127.0.0.1",
	port: Number(process.env.DB_PORT ?? 3306),
	user: process.env.DB_USER ?? "cap_user",
	password: process.env.DB_PASSWORD ?? "",
	database: process.env.DB_NAME ?? "cap",
}

// Observe application's life cycle to disconnect the datasource when
// application is stopped. This allows the application to be shut down
// gracefully. The `stop()` method is inherited from `juggler.DataSource`.
// Learn more at https://loopback.io/doc/en/lb4/Life-cycle.html
@lifeCycleObserver("datasource")
export class MariaDbDataSource
	extends juggler.DataSource
	implements LifeCycleObserver
{
	static dataSourceName = "MariaDB"
	static readonly defaultConfig = config

	constructor(
		@inject("datasources.config.MariaDB", { optional: true })
		dsConfig: object = config
	) {
		super(dsConfig)
	}
}
