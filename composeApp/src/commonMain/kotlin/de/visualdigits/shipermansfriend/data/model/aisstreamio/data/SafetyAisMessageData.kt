package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface SafetyAisMessageData : AisMessageData {

    val messageID :Int
    val repeatIndicator :Int
    val userID :Int
    val valid :Boolean
    val text: String
}
