package com.gonzalolozg.yape.presentation.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.domain.useCase.BaseUseCase
import com.gonzalolozg.yape.domain.useCase.home.GetRecipesUseCase
import com.gonzalolozg.yape.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
) : BaseViewModel() {

    private val _recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val recipes: LiveData<List<Recipe>> = _recipes

    private val _queryHintSearch = MutableLiveData<String>()
    val queryHintSearch: LiveData<String> = _queryHintSearch

    fun setQueryHintSearch(query: String) {
        _queryHintSearch.value = query
    }

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
