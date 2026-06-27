package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Destinations(
    @SerialName("0") val x0: X0? = X0(),
    @SerialName("1") val x1: X0? = X0(),
    @SerialName("2") val x2: X0? = X0(),
    @SerialName("3") val x3: X0? = X0()
)
