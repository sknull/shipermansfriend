package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.Serializable

@Serializable
sealed interface SafetyAisMessageData : AisMessageData {

    val messageId :Long
    val repeatIndicator :Long
    val mmsi: Long
    val valid :Boolean
    val text: String
}
