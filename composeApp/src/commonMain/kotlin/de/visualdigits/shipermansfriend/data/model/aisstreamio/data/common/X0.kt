package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class X0(
    @SerialName("DestinationID") val destinationMmmsi: Int? = null,
    @SerialName("Sequenceinteger") val sequenceinteger: Int? = null,
    @SerialName("Valid") val valid: Boolean? = null
)
