package de.visualdigits.shipermansfriend.data.model.wikidata

import kotlinx.serialization.Serializable

@Serializable
data class BindingValue(
    val dataType: String? = null,
    val type: String,
    val value: String
)
