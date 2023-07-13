package com.example.recipes_yape_android.presentation.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.domain.useCase.BaseUseCase
import com.example.recipes_yape_android.domain.useCase.home.GetRecipesUseCase
import com.example.recipes_yape_android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
) : BaseViewModel() {

    private val _recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val recipes: LiveData<List<Recipe>> = _recipes

    fun loadRecipes() = getRecipesUseCase(BaseUseCase.None(), viewModelScope) {
        it.either(
            ::handleFailure,
            ::handleRecipeList
        )
    }

    private fun handleRecipeList(recipes: List<Recipe>) {
        _recipes.value = recipes
    }

}