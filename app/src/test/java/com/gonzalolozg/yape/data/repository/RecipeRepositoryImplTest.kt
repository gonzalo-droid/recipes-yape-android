package com.gonzalolozg.yape.data.repository

import com.gonzalolozg.yape.core.functional.Either
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.data.remote.services.RecipeService
import com.gonzalolozg.yape.domain.repository.RecipeRepository
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RecipeRepositoryImplTest {

    private lateinit var repository: RecipeRepository

    @MockK
    private lateinit var service: RecipeService

    @Before
    fun setUp() {
        service = mockk()
        repository = RecipeRepositoryImpl(service)
    }

    @Test
    fun `should return recipes when service call is successful`() {

        val mockRecipes = ArrayList<Recipe>()
        val successResponse = Either.Right(mockRecipes)
        coEvery { service.recipes() } returns successResponse

        val result = runBlocking { repository.getRecipes() }

        assertEquals(successResponse, result)
    }

    @Test
    fun `should return failure when service call fails`() {

        val failureResponse = Either.Left(Failure.DefaultError("Error"))
        coEvery { service.recipes() } returns failureResponse

        val result = runBlocking { repository.getRecipes() }

        assertEquals(failureResponse, result)
    }

}