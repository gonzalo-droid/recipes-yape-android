package com.gonzalolozg.yape.presentation.features.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gonzalolozg.yape.core.extensions.failure
import com.gonzalolozg.yape.core.extensions.invisible
import com.gonzalolozg.yape.core.extensions.observe
import com.gonzalolozg.yape.core.extensions.visible
import com.gonzalolozg.yape.core.functional.Failure
import com.gonzalolozg.yape.data.remote.entity.Recipe
import com.gonzalolozg.yape.databinding.ActivityHomeBinding
import com.gonzalolozg.yape.presentation.features.detail.DetailActivity
import com.gonzalolozg.yape.presentation.utils.BindingUtil
import com.gonzalolozg.yape.presentation.utils.ConfigUtil
import com.gonzalolozg.yape.presentation.utils.MessageDesign
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var recipeRecycler: RecyclerView
    private var listRecipesFiler: List<Recipe> = arrayListOf()
    private lateinit var adapter: HomeRecipeAdapter

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

        binding.textSearch.addTextChangedListener { filter ->
            viewModel.setQueryHintSearch(filter.toString())
            var text = viewModel.queryHintSearch.value
            validQuerySearch(text)
        }

        with(binding.txtInputLayout) {
            setEndIconOnClickListener {
                viewModel.setQueryHintSearch("")
                binding.textSearch.text?.clear()
                validQuerySearch(viewModel.queryHintSearch.value)
            }
        }
    }

    private fun validQuerySearch(text: String?) {
        if (text?.isNotEmpty() == true) {
            val filterList = listRecipesFiler.filter { recipe ->
                recipe.name!!.lowercase().contains(text.toString().lowercase())
            }
            adapter.updateData(filterList)

            if (filterList.isEmpty()) {
                binding.noDataInclude.root.visible()
            } else {
                binding.noDataInclude.root.invisible()
            }
        } else {
            binding.noDataInclude.root.invisible()
            viewModel.loadRecipes()
        }
    }

    private fun renderRecipesList(recipes: List<Recipe>?) {
        hideProgress()

        binding.recipeRecycler.visibility = View.VISIBLE

        adapter = HomeRecipeAdapter(recipes = recipes ?: listOf()) { model ->
            onItemSelected(model)
        }
        recipeRecycler.adapter = adapter

        if (recipes != null) {
            listRecipesFiler = recipes
        }
    }

    private fun onItemSelected(model: Recipe) {
        startActivity(
            Intent(this, DetailActivity::class.java).apply {
                putExtra(ConfigUtil.RECIPE_PUT, model)
            }
        )
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
