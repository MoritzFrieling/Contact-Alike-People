import { ApplicationConfig, CapBackEndApplication } from "./application"
import https from "https"

export * from "./application"

import { Logger } from "./middleware/logger"

export async function main(options: ApplicationConfig = {}) {
	const app = new CapBackEndApplication(options)
	await app.boot()

	app.middleware(Logger)

	// TODO: update models
	await app.migrateSchema({
		existingSchema: "alter",
		models: [
			"User",
			"InterestType",
			"Interest",
			"UserInterest",
			"Social",
			"UserSocial",
			"UserAddress",
			"UserDevice",
			"Relationship",
		],
	})

	await app.start()

	const host = `http://localhost:${app.restServer.config.port}`

	console.log(`Server is running at ${host}/`)
	console.log(`API Explorer ${host}/explorer/`)
	//console.log(`Try ${ url }/ping`);

	setInterval(() => {
		https.get("https://prj4-and2-cap.herokuapp.com/")
	}, 1000 * 60 * 5) // ping every 5 minutes

	return app
}

if (require.main === module) {
	// Run the application
	const config = {
		rest: {
			port: +(process.env.PORT ?? 3000),
			host: process.env.HOST,
			// The `gracePeriodForClose` provides a graceful close for http/https
			// servers with keep-alive clients. The default value is `Infinity`
			// (don't force-close). If you want to immediately destroy all sockets
			// upon stop, set its value to `0`.
			// See https://www.npmjs.com/package/stoppable
			gracePeriodForClose: 5000, // 5 seconds
			openApiSpec: {
				// useful when used with OpenAPI-to-GraphQL to locate your application
				setServersFromRequest: true,
			},
		},
	}
	main(config).catch((err) => {
		console.error("Cannot start the application.", err)
		process.exit(1)
	})
}
