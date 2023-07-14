package com.gonzalolozg.yape.core.di


import com.gonzalolozg.yape.data.remote.services.RecipeService
import com.gonzalolozg.yape.data.repository.RecipeRepositoryImpl
import com.gonzalolozg.yape.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        service: RecipeService,
    ): RecipeRepository = RecipeRepositoryImpl(service)
}
