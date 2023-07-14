package com.gonzalolozg.yape.data.remote.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Recipe(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("instructions") val instructions: String?,
    @SerializedName("ingredients") val ingredients: String?,
    @SerializedName("image") var image: String?,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
) : Serializable
