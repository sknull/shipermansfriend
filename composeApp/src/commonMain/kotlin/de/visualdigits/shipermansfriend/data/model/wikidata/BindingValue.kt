package de.visualdigits.shipermansfriend.data.model.wikidata

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class BindingValue(
    val dataType: String? = null,
    val type: String,
    val value: String
)
