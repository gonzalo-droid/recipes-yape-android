package com.example.recipes_yape_android.presentation.features.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.recipes_yape_android.R
import com.example.recipes_yape_android.data.remote.entity.Recipe
import com.example.recipes_yape_android.databinding.ItemRecipeBinding
import com.example.recipes_yape_android.presentation.utils.imageLoader.ImageLoaderGlide


class HomeRecipeAdapter constructor(
    private val recipes: List<Recipe> = arrayListOf(),
    private val onClickListener: (Recipe) -> Unit,
) : RecyclerView.Adapter<HomeRecipeAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(recipes[position], onClickListener)
    }

    override fun getItemCount(): Int = recipes.size

    inner class CustomViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemRecipeBinding.bind(view)

        fun bind(model: Recipe, onClickListener: (Recipe) -> Unit) {

            binding.titleTv.text = model.name
            binding.descriptionTv.text = model.description

            model.image?.let {
                ImageLoaderGlide().loadImage(
                    imageView = binding.imageIv,
                    imagePath = it,
                    requestOptions = RequestOptions.bitmapTransform(RoundedCorners(16)),
                    placeHolder = R.drawable.food_loading
                )
            }

            itemView.setOnClickListener {
                onClickListener(model)
            }
        }
    }
}
