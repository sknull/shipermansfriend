package de.visualdigits.shipermansfriend.data.model.aisstreamio.status


import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.common.KmpOffsetDateTimeHeuristicDeserializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ServiceStatus(
    @SerialName("state") val state: ServiceState? = null,
    @Serializable(with = KmpOffsetDateTimeHeuristicDeserializer::class) @SerialName("lastChecked") val lastChecked: KmpOffsetDateTime? = null,
    @Serializable(with = KmpOffsetDateTimeHeuristicDeserializer::class) @SerialName("lastMessageReceived") val lastMessageReceived: KmpOffsetDateTime? = null,
    @SerialName("history") val history: List<History?>? = null,
    @SerialName("devMode") val devMode: Boolean? = null,
    @SerialName("simulated") val simulated: Boolean? = null,
    @SerialName("silenceTimeout") val silenceTimeout: Int? = null
)
