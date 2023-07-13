package com.example.recipes_yape_android.presentation.features.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.request.RequestOptions
import com.example.recipes_yape_android.R
import com.example.recipes_yape_android.core.extensions.failure
import com.example.recipes_yape_android.core.extensions.observe
import com.example.recipes_yape_android.core.functional.Failure
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.databinding.ActivityDetailBinding
import com.example.recipes_yape_android.presentation.features.maps.MapsActivity
import com.example.recipes_yape_android.presentation.utils.BindingUtil
import com.example.recipes_yape_android.presentation.utils.ConfigUtil
import com.example.recipes_yape_android.presentation.utils.imageLoader.ImageLoaderGlide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)

        val view = binding.root

        setContentView(view)

        val toolbar = binding.toolbar

        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val upArrow = resources.getDrawable(R.drawable.ic_arrow_back)

        supportActionBar!!.setHomeAsUpIndicator(upArrow)


        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        with(viewModel) {
            observe(detailRecipe, ::renderRecipes)
            failure(failure, ::handleFailure)
        }

        val data = intent.getSerializableExtra(ConfigUtil.RECIPE_PUT) as? Recipe
        if (data != null) {
            viewModel.setData(data)
        }
    }

    private fun renderRecipes(recipe: Recipe?) {

        recipe?.let { model ->
            with(binding) {
                toolbarLayout.title = " "
                titleTv.text = model.name
                descriptionText.text = model.description
                instructionsDescriptionTv.text = model.instructions
                titleTv.text = model.name

                var listIngredients = ""
                model.ingredients?.let { item ->
                    val array = item.split(";")
                    array.forEach { element ->
                        listIngredients += "- ${element} \n"
                    }
                }

                ingredientsDescriptionTv.text = listIngredients

                model.image?.let { image ->
                    ImageLoaderGlide().loadImage(
                        imageView = imageCourseIv,
                        imagePath = image,
                        requestOptions = RequestOptions.centerCropTransform(),
                        placeHolder = R.drawable.food_loading
                    )
                }


            }
            binding.mapCard.setOnClickListener {
                goToMap(model)
            }
        }
    }

    private fun goToMap(model: Recipe) {
        startActivity(Intent(this, MapsActivity::class.java).apply {
            putExtra(ConfigUtil.RECIPE_PUT, model)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleFailure(failure: Failure?) {
        BindingUtil.showSnackBar(binding.root, getString(R.string.error_show_data))
    }

}