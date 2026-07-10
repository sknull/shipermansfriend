package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
sealed interface SafetyAisMessageData : AisMessageData {

    val messageId :Long
    val repeatIndicator :Long
    val mmsi: Long
    val valid :Boolean
    val text: String
}
