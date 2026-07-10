package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ReportA(
    @SerialName("Name") val name: String,
    @SerialName("Valid") val valid: Boolean
)
