package com.gonzalolozg.yape.data.remote.network

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.entity.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject
import javax.inject.Singleton
import javax.net.ssl.SSLException

@Singleton
class NetworkHandler
@Inject constructor(val networkUtils: ConnectionUtils) {

    /**
     * Invoke the retrofit endpoint service in IO Context and after the response has been invoked
     * verify if its successful and if has a valid body.
     */
    suspend inline fun <T> callServiceBase(
        crossinline retrofitCall: suspend () -> Response<BaseResponse<T>>
    ): Either<Failure, T> {
        return when (networkUtils.isNetworkAvailable()) {
            true -> {
                try {
                    withContext(Dispatchers.IO) {
                        val response = retrofitCall.invoke()
                        if (response.isSuccessful && response.body() != null) {
                            return@withContext Either.Right(response.body()!!.data)
                        } else {
                            return@withContext Either.Left(
                                getErrorMessageFromServer(
                                    response.code(), response.errorBody()?.string()
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    return Either.Left(parseException(e))
                }
            }
            false -> Either.Left(Failure.NoNetworkDetected)
        }
    }

    /**
     * Parse Server Error to [Failure.ServerBodyError] if [errorBody] [isServerErrorValid].
     * @return [Failure] object.
     */
    suspend fun getErrorMessageFromServer(code: Int, errorBody: String?): Failure {
        return if (errorBody != null) {
            return withContext(Dispatchers.IO) {
                val serverErrorJson = JSONObject(errorBody)
                when {
                    isServerErrorValid(serverErrorJson.toString()) -> {
                        if (code == 401 || code == 403) {
                            return@withContext Failure.UnauthorizedOrForbidden(serverErrorJson[KEY_MESSAGE].toString())
                        } else {
                            return@withContext Failure.ServerBodyError(
                                code,
                                serverErrorJson[KEY_MESSAGE].toString()
                            )
                        }
                    }
                    serverErrorJson.toString().contains("\"$KEY_MESSAGE\"") -> {
                        return@withContext Failure.ServiceUncaughtFailure(serverErrorJson[KEY_MESSAGE].toString())
                    }
                    else -> return@withContext Failure.None
                }
            }
        } else {
            Failure.None
        }
    }

    private fun isServerErrorValid(error: String): Boolean {
        return error.contains("\"$KEY_MESSAGE\"")
    }

    fun parseException(throwable: Throwable): Failure {
        return when (throwable) {
            is SocketTimeoutException -> Failure.TimeOut
            is SSLException -> Failure.NetworkConnectionLostSuddenly
            else -> Failure.ServiceUncaughtFailure(throwable.message.toString())
        }
    }

    companion object {
        private const val KEY_CODE = "status" // code
        private const val KEY_MESSAGE = "message"
    }
}
