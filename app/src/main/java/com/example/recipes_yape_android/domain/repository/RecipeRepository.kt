package com.example.recipes_yape_android.domain.repository

import com.example.recipes_yape_android.core.functional.Either
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.entity.Recipe


interface RecipeRepository {

    suspend fun getRecipes(): Either<Failure, ArrayList<Recipe>>
}
