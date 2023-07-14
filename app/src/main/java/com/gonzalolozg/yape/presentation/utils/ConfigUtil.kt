package com.gonzalolozg.yape.presentation.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object ConfigUtil {

    const val RECIPE_PUT = "recipe"


    fun hideSoftInput(activity: Activity?) {
        if (activity != null) {
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            val inputManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}