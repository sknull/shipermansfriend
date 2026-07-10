package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.serializer.AisMessageDataUnwrappingSerializer
import kotlinx.serialization.Serializable

@Immutable
@Serializable(with = AisMessageDataUnwrappingSerializer::class)
sealed interface AisMessageData
