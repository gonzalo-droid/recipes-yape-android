package com.example.recipes_yape_android.presentation.features.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipes_yape_android.core.extensions.failure
import com.example.recipes_yape_android.core.extensions.observe
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.databinding.ActivityHomeBinding
import com.example.recipes_yape_android.presentation.utils.BindingUtil
import com.example.recipes_yape_android.presentation.utils.MessageDesign
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var recipeRecycler: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]


        recipeRecycler = binding.recipeRecycler

        recipeRecycler.layoutManager = LinearLayoutManager(this@HomeActivity)

        binding.recipeRecycler.visibility = View.INVISIBLE

        showProgress()

        with(viewModel) {
            observe(recipes, ::renderRecipesList)
            failure(failure, ::handleFailure)
        }

        viewModel.loadRecipes()
    }

    private fun renderRecipesList(recipes: List<Recipe>?) {
        hideProgress()

        binding.recipeRecycler.visibility = View.VISIBLE
        recipeRecycler.adapter = HomeRecipeAdapter(recipes = recipes ?: listOf()) { model ->
            onItemSelected(model)
        }
    }

    private fun onItemSelected(model: Recipe) {

    }

    private fun handleFailure(failure: Failure?) {
        hideProgress()

        val messageDesign: MessageDesign = BindingUtil.reducerFailure(failure)

        BindingUtil.showSnackBar(binding.root, getString(messageDesign.idMessage))
    }

    private fun showProgress() {
        binding.skeletonInclude.root.visibility = View.VISIBLE

    }

    private fun hideProgress() {
        binding.skeletonInclude.root.visibility = View.INVISIBLE
    }
}