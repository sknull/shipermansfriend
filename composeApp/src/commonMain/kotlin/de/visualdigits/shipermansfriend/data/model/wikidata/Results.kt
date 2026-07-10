package de.visualdigits.shipermansfriend.data.model.wikidata


import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Results(
    @SerialName("bindings") val bindings: List<VesselBinding> = listOf()
)
