package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Data
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class DataLinkManagementMessage(
    @SerialName("Data") val `data`: Data,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare") val spare: Long,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") val valid: Boolean
) : AisMessageData
