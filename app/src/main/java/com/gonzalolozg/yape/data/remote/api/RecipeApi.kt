package com.gonzalolozg.yape.data.remote.api

import com.gonzalolozg.yape.data.remote.entity.BaseResponse
import com.gonzalolozg.yape.data.remote.entity.Recipe
import retrofit2.Response
import retrofit2.http.GET

interface RecipeApi {

    @GET("recipes")
    suspend fun recipes(): Response<BaseResponse<ArrayList<Recipe>>>

}