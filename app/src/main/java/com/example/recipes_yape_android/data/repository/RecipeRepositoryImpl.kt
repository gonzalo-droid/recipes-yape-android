package com.example.recipes_yape_android.data.repository

import com.example.recipes_yape_android.core.functional.Either
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.data.remote.services.RecipeService
import com.example.recipes_yape_android.domain.repository.RecipeRepository
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

