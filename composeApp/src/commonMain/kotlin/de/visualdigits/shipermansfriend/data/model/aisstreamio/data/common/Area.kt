package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Area(
    @SerialName("Latitude1") val latitude1: Double,
    @SerialName("Latitude2") val latitude2: Double,
    @SerialName("Longitude1") val longitude1: Double,
    @SerialName("Longitude2") val longitude2: Double
)
