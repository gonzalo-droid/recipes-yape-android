package com.gonzalolozg.yape.domain.useCase.home

import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.domain.repository.RecipeRepository
import com.gonzalolozg.yape.domain.useCase.BaseUseCase
import javax.inject.Inject

class GetRecipesUseCase
@Inject constructor(private val recipeRepository: RecipeRepository) :
    BaseUseCase<ArrayList<Recipe>, BaseUseCase.None>() {

    override suspend fun run(params: None) = recipeRepository.getRecipes()
}
