package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Destinations
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class CompressionEnabled(
    @SerialName("CompressionEnabled") val value: Boolean? = null
) : AisMessageData
