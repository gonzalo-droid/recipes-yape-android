package com.example.recipes_yape_android.data.remote.api

import com.example.recipes_yape_android.data.remote.entity.BaseResponse
import com.example.recipes_yape_android.data.remote.entity.Recipe
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {

    @GET("https://demo1784653.mockable.io/recipes")
    suspend fun recipes(): Response<BaseResponse<ArrayList<Recipe>>>

}