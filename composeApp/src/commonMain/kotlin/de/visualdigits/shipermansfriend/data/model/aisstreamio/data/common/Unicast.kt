package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Unicast(
    @SerialName("AddressStation1") val addressStation1: Int,
    @SerialName("AddressStation2") val addressStation2: Int,
    @SerialName("Spare2") val spare2: Int,
    @SerialName("Spare3") val spare3: Int
)
