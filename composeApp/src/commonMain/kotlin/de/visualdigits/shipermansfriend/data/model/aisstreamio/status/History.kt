package de.visualdigits.shipermansfriend.data.model.aisstreamio.status


import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.common.KmpOffsetDateTimeHeuristicDeserializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class History(
    @Serializable(with = KmpOffsetDateTimeHeuristicDeserializer::class) @SerialName("timestamp") val timestamp: KmpOffsetDateTime? = null,
    @SerialName("state") val state: String? = null
)
