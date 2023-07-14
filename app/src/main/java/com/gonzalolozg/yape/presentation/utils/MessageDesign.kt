package com.gonzalolozg.yape.presentation.utils

import com.gonzalolozg.yape.R

class MessageDesign private constructor(
    val idMessage: Int = R.string.error_user_message,
    val messageString: String? = ""
) {
    data class Builder(
        var idMessage: Int = R.string.error_user_message,
        var messageString: String? = ""
    ) {
        fun idMessage(idMessage: Int) = apply { this.idMessage = idMessage }
        fun messageString(messageString: String) = apply {
            this.messageString = messageString.toString()
        }

        fun build() = MessageDesign(idMessage, messageString)
    }
}
