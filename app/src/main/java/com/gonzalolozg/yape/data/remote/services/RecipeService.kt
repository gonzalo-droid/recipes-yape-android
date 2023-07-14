package com.gonzalolozg.yape.data.remote.services

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.entity.Recipe

interface RecipeService {

    suspend fun recipes(): Either<Failure, ArrayList<Recipe>>

}
