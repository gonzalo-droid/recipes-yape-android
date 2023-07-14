package com.gonzalolozg.yape.data.remote.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BaseResponse<T>(
    @SerializedName("code") val code: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: T
) : Serializable
