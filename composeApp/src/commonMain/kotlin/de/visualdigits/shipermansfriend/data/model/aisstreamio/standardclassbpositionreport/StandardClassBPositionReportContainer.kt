package de.visualdigits.shipermansfriend.data.model.aisstreamio.standardclassbpositionreport

import de.visualdigits.shipermansfriend.data.model.aisstreamio.position.PositionAisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StandardClassBPositionReportContainer(
    @SerialName("StandardClassBPositionReport") override val data: StandardClassBPositionReport
) : PositionAisMessageContainer<StandardClassBPositionReportContainer> {
    override fun toString(): String {
        return data.toString()
    }
}

