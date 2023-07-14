package com.gonzalolozg.yape.domain.useCase.home

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.domain.repository.RecipeRepository
import com.gonzalolozg.yape.domain.useCase.BaseUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
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
