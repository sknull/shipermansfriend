package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ApplicationID(
    @SerialName("DesignatedAreaCode") val designatedAreaCode: Long? = null,
    @SerialName("FunctionIdentifier") val functionIdentifier: Long? = null,
    @SerialName("Valid") val valid: Boolean? = null
)
