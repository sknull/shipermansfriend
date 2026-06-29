package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Dimension
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ReportA
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.ReportB
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StaticDataReport(
    @SerialName("MessageID") val messageId: Int,
    @SerialName("PartNumber") val partNumber: Boolean,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("ReportA") val reportA: ReportA,
    @SerialName("ReportB") val reportB: ReportB,
    @SerialName("Reserved") val reserved: Int,
    @SerialName("UserID") val mmsi: Int,
    @SerialName("Valid") override val valid: Boolean
) : StaticDataAisMessageData {
    override fun toString(): String {
        return "Ship static data: shipType=${reportB.shipType.name} type=${reportB.shipType.category.name} callsign=${reportB.callSign}."
    }

    override val imoNumber: Long
        get() = 0

    override val callSign: String
        get() = reportB.callSign

    override val dimension: Dimension
        get() = reportB.dimension

    override val destination: String
        get() = ""

    override val shipType: ShipType
        get() = reportB.shipType

    override val maximumStaticDraught: Double
        get() = 0.0
}
