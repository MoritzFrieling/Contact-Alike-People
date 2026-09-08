const FCM = require("fcm-push")

const SERVER_KEY =
	"AAAARczlJJM:APA91bFb67A74NKd0VaKoAyZ5TB3b-cvpCEKT24sy9vkdHX7rDfldtdg6LEah6HoU9837amuvmAWkoKOy4zZTcHtaiJaeFpiItRFTo3dGFeqA8JeIFTp-3Wr0yJaipGNAatiDjNP1BvR"

export interface CloudMessage {
	to: string | `/topics/${string}`
	collapse_key: string
	data: object
	notification: {
		title: string
		body: string
	}
}

export class FirebaseCloudMessaging {
	private client
	private static inst: FirebaseCloudMessaging

	static getInstance() {
		if (FirebaseCloudMessaging.inst === undefined) {
			FirebaseCloudMessaging.inst = new FirebaseCloudMessaging()
		}

		return FirebaseCloudMessaging.inst
	}

	constructor() {
		this.client = new FCM(SERVER_KEY)
	}

	sendMessage(message: Omit<CloudMessage, "to">, deviceIdList: string[]) {
		for (const deviceId of deviceIdList) {
			const fullMsg: CloudMessage = {
				...message,
				to: deviceId,
			}

			console.debug(`FirebaseCloudMessaging::sendMessage | sending to '${deviceId}'`)
			this.client.send(fullMsg, (err: never, response: never) => {
				if (err) {
					console.error("FirebaseCloudMessaging::sendMessage | err:", err)
				} else {
					//console.debug("FirebaseCloudMessaging::sendMessage | success:", response)
				}
			})
		}
	}
}
