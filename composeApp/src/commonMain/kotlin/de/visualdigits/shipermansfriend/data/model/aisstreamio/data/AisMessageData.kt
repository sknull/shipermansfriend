package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.serializer.AisMessageDataUnwrappingSerializer
import kotlinx.serialization.Serializable

@Serializable(with = AisMessageDataUnwrappingSerializer::class)
sealed interface AisMessageData
