package de.visualdigits.shipermansfriend.data.model.aisstreamio.status


import androidx.compose.runtime.Immutable
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.common.KmpOffsetDateTimeHeuristicDeserializer
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.AisStreamState
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class AisStreamStatus(
    @SerialName("state") val state: AisStreamState? = null,
    @Serializable(with = KmpOffsetDateTimeHeuristicDeserializer::class) @SerialName("lastChecked") val lastChecked: KmpOffsetDateTime? = null,
    @Serializable(with = KmpOffsetDateTimeHeuristicDeserializer::class) @SerialName("lastMessageReceived") val lastMessageReceived: KmpOffsetDateTime? = null,
    @SerialName("history") val history: List<History?>? = null,
    @SerialName("devMode") val devMode: Boolean? = null,
    @SerialName("simulated") val simulated: Boolean? = null,
    @SerialName("silenceTimeout") val silenceTimeout: Int? = null
)
