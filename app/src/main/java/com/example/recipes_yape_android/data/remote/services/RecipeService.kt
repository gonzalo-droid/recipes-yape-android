package com.example.recipes_yape_android.data.remote.services

import com.example.recipes_yape_android.core.functional.Either
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.entity.Recipe

interface RecipeService {

    suspend fun recipes(): Either<Failure, ArrayList<Recipe>>

}
