package com.example.recipes_yape_android.domain.useCase.home

import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.domain.repository.RecipeRepository
import com.example.recipes_yape_android.domain.useCase.BaseUseCase
import javax.inject.Inject

class GetRecipesUseCase
@Inject constructor(private val recipeRepository: RecipeRepository) :
    BaseUseCase<ArrayList<Recipe>, BaseUseCase.None>() {

    override suspend fun run(params: None) = recipeRepository.getRecipes()
}
