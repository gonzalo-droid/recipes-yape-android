package com.gonzalolozg.yape.data.remote.services

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.api.RecipeApi
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.data.remote.network.NetworkHandler
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