package de.visualdigits.shipermansfriend.data.model.wikidata


import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class Head(
    @SerialName("vars") val vars: List<String?>
)
