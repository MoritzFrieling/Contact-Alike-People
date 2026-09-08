import {
	Count,
	CountSchema,
	Filter,
	FilterExcludingWhere,
	repository,
	Where,
} from "@loopback/repository"
import {
	post,
	param,
	get,
	getModelSchemaRef,
	patch,
	put,
	del,
	requestBody,
	response,
} from "@loopback/rest"
import { Social } from "../models"
import { SocialRepository } from "../repositories"

export class SocialController {
	constructor(
		@repository(SocialRepository)
		public socialRepository: SocialRepository
	) {}

	@get("/socials")
	@response(200, {
		description: "Array of Social model instances",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(Social, {
						includeRelations: true,
					}),
				},
			},
		},
	})
	async find(
		@param.filter(Social) filter?: Filter<Social>
	): Promise<Social[]> {
		return this.socialRepository.find(filter)
	}

	@get("/socials/{id}")
	@response(200, {
		description: "Social model instance",
		content: {
			"application/json": {
				schema: getModelSchemaRef(Social, { includeRelations: true }),
			},
		},
	})
	async findById(
		@param.path.number("id") id: number,
		@param.filter(Social, { exclude: "where" })
		filter?: FilterExcludingWhere<Social>
	): Promise<Social> {
		return this.socialRepository.findById(id, filter)
	}
}
