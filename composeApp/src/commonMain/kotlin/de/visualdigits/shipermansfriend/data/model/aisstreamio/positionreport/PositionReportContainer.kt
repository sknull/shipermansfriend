package de.visualdigits.shipermansfriend.data.model.aisstreamio.positionreport

import de.visualdigits.shipermansfriend.data.model.aisstreamio.position.PositionAisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PositionReportContainer(
    @SerialName("PositionReport") override val data: PositionReport
) : PositionAisMessageContainer<PositionReportContainer> {
    override fun toString(): String {
        return data.toString()
    }
}
