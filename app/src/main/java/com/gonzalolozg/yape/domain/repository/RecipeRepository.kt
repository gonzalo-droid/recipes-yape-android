package com.gonzalolozg.yape.domain.repository

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.entity.Recipe

interface RecipeRepository {

    suspend fun getRecipes(): Either<Failure, ArrayList<Recipe>>
}
