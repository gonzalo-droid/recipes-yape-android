package com.example.recipes_yape_android.presentation.utils

import android.view.View
import com.example.recipes_yape_android.R
import com.example.recipes_yape_android.core.functional.Failure
import com.google.android.material.snackbar.Snackbar

class BindingUtil {

    companion object {

        fun showSnackBar(
            rootView: View,
            contentText: String?,
            duration: Int = Snackbar.LENGTH_LONG,
        ) {
            Snackbar.make(
                rootView,
                contentText ?: rootView.context.getString(R.string.error_user_message),
                duration
            ).show()
        }

        fun reducerFailure(failure: Failure?): MessageDesign {
            return when (failure) {
                is Failure.UnauthorizedOrForbidden -> {
                    MessageDesign.Builder().idMessage(R.string.error_user_message).build()
                }

                is Failure.NetworkConnectionLostSuddenly -> {
                    MessageDesign.Builder().idMessage(R.string.error_no_internet).build()
                }

                is Failure.NoNetworkDetected -> {
                    MessageDesign.Builder().idMessage(R.string.error_no_internet).build()
                }

                is Failure.TimeOut -> {
                    MessageDesign.Builder().idMessage(R.string.error_server).build()
                }

                is Failure.ServerBodyError -> {
                    MessageDesign.Builder().idMessage(R.string.error_user_message).build()
                }


                is Failure.DefaultError -> {
                    MessageDesign.Builder().messageString(failure.message).build()
                }

                else -> {
                    MessageDesign.Builder().idMessage(R.string.error_user_message).build()
                }
            }
        }
    }
}
