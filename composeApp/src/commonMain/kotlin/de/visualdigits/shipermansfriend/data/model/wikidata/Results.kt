package de.visualdigits.shipermansfriend.data.model.wikidata


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Results(
    @SerialName("bindings") val bindings: List<VesselBinding> = listOf()
)
