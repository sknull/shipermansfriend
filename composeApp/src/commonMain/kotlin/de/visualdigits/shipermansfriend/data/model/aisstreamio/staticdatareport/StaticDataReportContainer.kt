package de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdatareport

import de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata.StaticDataAisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StaticDataReportContainer(
    @SerialName("StaticDataReport") override val data: StaticDataReport
) : StaticDataAisMessageContainer<StaticDataReportContainer> {
    override fun toString(): String {
        return data.toString()
    }
}

