package com.example.recipes_yape_android.data.remote.services

import com.example.recipes_yape_android.core.functional.Either
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.api.RecipeApi
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.data.remote.network.NetworkHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeServiceImpl @Inject constructor(
    private val recipeApi: RecipeApi,
    private val networkHandler: NetworkHandler,
) : RecipeService {

    override suspend fun recipes(): Either<Failure, ArrayList<Recipe>> {
        return networkHandler.callServiceBase {
            recipeApi.recipes()
        }
    }

}