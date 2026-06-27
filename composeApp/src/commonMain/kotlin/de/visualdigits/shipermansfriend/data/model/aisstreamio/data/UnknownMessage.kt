package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import kotlinx.serialization.Serializable

@Serializable
data class UnknownMessage(
    val value: String? = null
) : AisMessageData
