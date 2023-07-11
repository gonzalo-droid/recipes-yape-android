package com.example.recipes_yape_android.presentation.utils.imageLoader

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImageLoaderGlide : ImageLoader {

    override fun loadImage(imageView: ImageView, imagePath: String, requestOptions: RequestOptions, placeHolder: Int) {
        Glide.with(imageView.context)
            .load(imagePath)
            .apply(requestOptions)
            .placeholder(placeHolder)
            .into(imageView)
    }

    override fun loadImage(imageView: ImageView, imagePath: Int, requestOptions: RequestOptions) {
        Glide.with(imageView.context)
            .load(imagePath)
            .apply(requestOptions)
            .into(imageView)
    }

    override fun loadImage(imageView: ImageView, imagePath: Int) {
        Glide.with(imageView.context)
            .load(imagePath)
            .into(imageView)
    }

    override fun loadImage(imageView: ImageView, imagePath: String) {
        Glide.with(imageView.context)
            .load(imagePath)
            .into(imageView)
    }
}
