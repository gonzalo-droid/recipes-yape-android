package com.gonzalolozg.yape.presentation.features.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.presentation.base.BaseViewModel
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