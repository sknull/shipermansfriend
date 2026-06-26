package de.visualdigits.shipermansfriend.domain.model.aisstreamio

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiKey(
    @SerialName("APIKey") val apiKey: String,
    @SerialName("BoundingBoxes") val boundingBoxes: List<List<List<Double>>>,
    @SerialName("FiltersShipMMSI") val filterShipMmsi: List<Double> = listOf(),
    @SerialName("FilterMessageTypes") val filterMessageTypes: List<String> = listOf()
)
