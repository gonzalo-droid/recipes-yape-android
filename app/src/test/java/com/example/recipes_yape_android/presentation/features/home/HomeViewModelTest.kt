package com.example.recipes_yape_android.presentation.features.home

import com.example.recipes_yape_android.core.functional.Either.Right
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.domain.useCase.home.GetRecipesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var getRecipesUseCase: GetRecipesUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        getRecipesUseCase = mockk()

        viewModel = HomeViewModel(getRecipesUseCase)
    }

    @Test
    fun `loading recipes should update live data`() {
        val recipesList = arrayListOf<Recipe>(
            Recipe(
                id = 1,
                name = "Arroz con pato",
                description = "",
                instructions = "",
                ingredients = "",
                image = ""
            ), Recipe(
                id = 2,
                name = "Chaufa",
                description = "",
                instructions = "",
                ingredients = "",
                image = ""
            )
        )

        // Create a test dispatcher and test scope
        val testDispatcher = StandardTestDispatcher()
        val testScope = TestScope(testDispatcher)

        testScope.launch {
            coEvery { getRecipesUseCase.run(any()) } returns Right(recipesList)

            viewModel.recipes.observeForever {
                Assert.assertEquals(it!!.size, 2)
                Assert.assertEquals(it[0].id, 1)
                Assert.assertEquals(it[0].name, "Arroz con pato")
                Assert.assertEquals(it[1].id, 2)
                Assert.assertEquals(it[1].name, "Chaufa")
            }

            viewModel.loadRecipes()

            coVerify(exactly = 1) { getRecipesUseCase.run(any()) }
        }
    }
}