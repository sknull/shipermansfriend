package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Eta(
    @SerialName("Day") val day: Long,
    @SerialName("Month") val month: Long,
    @SerialName("Hour") val hour: Long,
    @SerialName("Minute") val minute: Long
) {
    override fun toString(): String {
        val day = if((day?:0) > 0  && (month?:0) > 0) {
            "${day.toString().padStart(2, '0')}.${month.toString().padStart(2, '0')}."
        } else {
            "Today"
        }
        val time = "${((hour?:0) % 24).toString().padStart(2, '0')}:${((minute ?: 0) % 60).toString().padStart(2, '0')}"
        return  "$day $time"
    }
}
