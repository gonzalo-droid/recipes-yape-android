package com.example.recipes_yape_android.core.di


import com.example.recipes_yape_android.data.remote.services.RecipeService
import com.example.recipes_yape_android.data.repository.RecipeRepositoryImpl
import com.example.recipes_yape_android.domain.repository.RecipeRepository
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
