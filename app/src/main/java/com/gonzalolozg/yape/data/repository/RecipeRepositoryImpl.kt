package com.gonzalolozg.yape.data.repository

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.data.remote.services.RecipeService
import com.gonzalolozg.yape.domain.repository.RecipeRepository
import javax.inject.Inject


class RecipeRepositoryImpl
@Inject constructor(
    private val service: RecipeService,
) : RecipeRepository {
    override suspend fun getRecipes(): Either<Failure, ArrayList<Recipe>> {
        return when (val response = service.recipes()) {
            is Either.Right -> {
                Either.Right(response.b)
            }

            is Either.Left -> Either.Left(response.a)
        }
    }
}

