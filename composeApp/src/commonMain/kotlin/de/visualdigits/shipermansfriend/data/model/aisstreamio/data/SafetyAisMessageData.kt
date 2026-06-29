package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface SafetyAisMessageData : AisMessageData {

    val messageId :Int
    val repeatIndicator :Int
    val mmsi: Long
    val valid :Boolean
    val text: String
}
