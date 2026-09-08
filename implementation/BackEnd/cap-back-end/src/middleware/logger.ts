import { MiddlewareContext } from "@loopback/rest"
import { InvocationResult, Next } from "@loopback/core"

import util from "util"

const colors = {
	Reset: "\x1b[0m",
	Bright: "\x1b[1m",
	Dim: "\x1b[2m",
	Underscore: "\x1b[4m",
	Blink: "\x1b[5m",
	Reverse: "\x1b[7m",
	Hidden: "\x1b[8m",

	FgBlack: "\x1b[30m",
	FgRed: "\x1b[31m",
	FgGreen: "\x1b[32m",
	FgYellow: "\x1b[33m",
	FgBlue: "\x1b[34m",
	FgMagenta: "\x1b[35m",
	FgCyan: "\x1b[36m",
	FgWhite: "\x1b[37m",

	BgBlack: "\x1b[40m",
	BgRed: "\x1b[41m",
	BgGreen: "\x1b[42m",
	BgYellow: "\x1b[43m",
	BgBlue: "\x1b[44m",
	BgMagenta: "\x1b[45m",
	BgCyan: "\x1b[46m",
	BgWhite: "\x1b[47m",
}

export async function Logger(
	middlewareCtx: MiddlewareContext,
	next: Next
): Promise<InvocationResult> {
	const { request, response } = middlewareCtx
	let statusColor

	response.on("finish", () => {
		const code = response.statusCode

		if (code < 300) {
			// Success
			statusColor = colors.FgGreen
		} else if (code < 400) {
			// Redirect
			statusColor = colors.FgCyan
		} else if (code < 500) {
			// Client-Error
			statusColor = colors.FgRed
		} else if (code < 600) {
			// Server-Error
			statusColor = colors.FgMagenta
		} else {
			statusColor = colors.Reset
		}

		console.log(
			"Middleware::Logger |",
			`${colors.FgGreen}${request.method}`,
			`${colors.FgCyan}${request.originalUrl}`,
			`${colors.Reset}| status:`,
			response.statusCode,
			`${statusColor}${response.statusMessage}${colors.Reset}`
		)
	})

	try {
		return await next()
	} catch (err) {
		console.log(
			"Middleware::Logger | Error Details ",
			util.inspect(JSON.parse(JSON.stringify(err)), {
				showHidden: false,
				depth: null,
			})
		)
		throw err
	}
}
