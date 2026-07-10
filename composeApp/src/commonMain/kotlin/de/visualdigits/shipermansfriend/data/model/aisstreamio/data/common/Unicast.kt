package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Unicast(
    @SerialName("AddressStation1") val addressStation1: Long,
    @SerialName("AddressStation2") val addressStation2: Long,
    @SerialName("Spare2") val spare2: Long,
    @SerialName("Spare3") val spare3: Long
)
