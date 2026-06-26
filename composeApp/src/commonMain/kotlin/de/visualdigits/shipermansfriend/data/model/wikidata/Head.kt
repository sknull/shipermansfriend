package de.visualdigits.shipermansfriend.data.model.wikidata


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Head(
    @SerialName("vars") val vars: List<String?>
)
