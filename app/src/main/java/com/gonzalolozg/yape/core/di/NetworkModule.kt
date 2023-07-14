package com.gonzalolozg.yape.core.di

import android.content.Context
import com.gonzalolozg.yape.BuildConfig
import com.gonzalolozg.yape.core.utils.SupportInterceptor
import com.gonzalolozg.yape.data.remote.api.RecipeApi
import com.gonzalolozg.yape.data.remote.network.ConnectionUtils
import com.gonzalolozg.yape.data.remote.network.ConnectionUtilsImpl
import com.gonzalolozg.yape.data.remote.network.NetworkHandler
import com.gonzalolozg.yape.data.remote.services.RecipeService
import com.gonzalolozg.yape.data.remote.services.RecipeServiceImpl
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Suppress("PrivatePropertyName")
    private val TIMEOUT: Long = 10

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        providesGsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(okHttpClient)
            .addConverterFactory(providesGsonConverterFactory).build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).serializeNulls()
            .create()
        return GsonConverterFactory.create(gson)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: SupportInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder().connectTimeout(com.gonzalolozg.yape.core.di.NetworkModule.TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(com.gonzalolozg.yape.core.di.NetworkModule.TIMEOUT, TimeUnit.MINUTES).readTimeout(
                com.gonzalolozg.yape.core.di.NetworkModule.TIMEOUT, TimeUnit.MINUTES)
            .addInterceptor(interceptor).addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().build())
            }.addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(OkHttpProfilerInterceptor())
        }

        return client.build()
    }

    @Provides
    @Singleton
    fun provideConnection(@ApplicationContext appContext: Context): ConnectionUtils =
        ConnectionUtilsImpl(appContext)

    @Provides
    @Singleton
    fun provideRecipeApi(
        retrofit: Retrofit,
    ): RecipeApi = retrofit.create(RecipeApi::class.java)

    @Provides
    @Singleton
    fun provideRecipeService(
        recipeApi: RecipeApi,
        networkHandler: NetworkHandler
    ): RecipeService = RecipeServiceImpl(recipeApi, networkHandler)
}
