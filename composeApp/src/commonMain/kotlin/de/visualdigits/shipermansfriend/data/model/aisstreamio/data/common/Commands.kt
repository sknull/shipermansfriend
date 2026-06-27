package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Commands(
    @SerialName("0") val x0: X0,
    @SerialName("1") val x1: X0
)
