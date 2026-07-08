package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplicationID(
    @SerialName("DesignatedAreaCode") val designatedAreaCode: Long? = null,
    @SerialName("FunctionIdentifier") val functionIdentifier: Long? = null,
    @SerialName("Valid") val valid: Boolean? = null
)
