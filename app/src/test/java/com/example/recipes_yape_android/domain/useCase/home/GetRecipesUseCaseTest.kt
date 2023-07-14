package com.example.recipes_yape_android.domain.useCase.home

import com.example.recipes_yape_android.core.functional.Either
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.domain.repository.RecipeRepository
import com.example.recipes_yape_android.domain.useCase.BaseUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRecipesUseCaseTest {

    private lateinit var getRecipesUseCase: GetRecipesUseCase

    @MockK
    private lateinit var repository: RecipeRepository

    @Before
    fun setUp() {
        repository = mockk()

        getRecipesUseCase = GetRecipesUseCase(repository)

        val mockRecipes = ArrayList<Recipe>()

        coEvery { repository.getRecipes() } returns Either.Right(mockRecipes)
    }

    @Test
    fun `should get data from repository`() {

        runBlocking { getRecipesUseCase.run(BaseUseCase.None()) }

        coVerify(exactly = 1) { repository.getRecipes() }
    }


}