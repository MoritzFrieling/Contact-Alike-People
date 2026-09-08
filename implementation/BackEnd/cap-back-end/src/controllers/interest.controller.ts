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
import { Interest } from "../models"
import { InterestRepository } from "../repositories"

export class InterestController {
	constructor(
		@repository(InterestRepository)
		public interestRepository: InterestRepository
	) {}

	@get("/interests")
	@response(200, {
		description: "Array of Interest model instances",
		content: {
			"application/json": {
				schema: {
					type: "array",
					items: getModelSchemaRef(Interest, {
						includeRelations: true,
					}),
				},
			},
		},
	})
	async find(
		@param.filter(Interest) filter?: Filter<Interest>
	): Promise<Interest[]> {
		return this.interestRepository.find(filter)
	}

	@get("/interests/{id}")
	@response(200, {
		description: "Interest model instance",
		content: {
			"application/json": {
				schema: getModelSchemaRef(Interest, { includeRelations: true }),
			},
		},
	})
	async findById(
		@param.path.number("id") id: number,
		@param.filter(Interest, { exclude: "where" })
		filter?: FilterExcludingWhere<Interest>
	): Promise<Interest> {
		return this.interestRepository.findById(id, filter)
	}
}
