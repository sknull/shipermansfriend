package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class X0(
    @SerialName("DestinationID") val destinationMmmsi: Long? = null,
    @SerialName("Sequenceinteger") val sequenceinteger: Long? = null,
    @SerialName("Valid") val valid: Boolean? = null
)
