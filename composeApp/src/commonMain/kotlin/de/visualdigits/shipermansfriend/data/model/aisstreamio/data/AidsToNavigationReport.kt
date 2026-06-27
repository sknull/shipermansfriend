package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Dimension
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AidsToNavigationReport(
    @SerialName("AssignedMode") val assignedMode: Boolean,
    @SerialName("AtoN") val atoN: Int,
    @SerialName("Dimension") val dimension: Dimension,
    @SerialName("Fixtype") val fixtype: Int,
    @SerialName("Latitude") val latitude: Double,
    @SerialName("Longitude") val longitude: Double,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("Name") val name: String,
    @SerialName("NameExtension") val nameExtension: String,
    @SerialName("OffPosition") val offPosition: Boolean,
    @SerialName("PositionAccuracy") val positionAccuracy: Boolean,
    @SerialName("Raim") val raim: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Boolean,
    @SerialName("Timestamp") val timestamp: Int,
    @SerialName("Type") val type: Int,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") val valid: Boolean,
    @SerialName("VirtualAtoN") val virtualAtoN: Boolean
) : AisMessageData
