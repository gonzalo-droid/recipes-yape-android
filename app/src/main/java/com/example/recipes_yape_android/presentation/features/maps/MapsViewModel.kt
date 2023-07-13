package com.example.recipes_yape_android.presentation.features.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor() : BaseViewModel() {

    private val _detailRecipe: MutableLiveData<Recipe> = MutableLiveData()
    val detailRecipe: LiveData<Recipe> = _detailRecipe

    fun setData(model: Recipe){
        _detailRecipe.postValue(model)
    }

    fun getData(): Recipe? {
        return detailRecipe.value
    }

}