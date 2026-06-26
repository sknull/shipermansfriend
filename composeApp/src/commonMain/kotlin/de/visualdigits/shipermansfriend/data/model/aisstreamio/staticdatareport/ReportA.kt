package de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdatareport


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportA(
    @SerialName("Name") val name: String,
    @SerialName("Valid") val valid: Boolean
)
