package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdatareport.StaticDataReportContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("StaticDataReport")
class StaticDataReportWrapper(
    @SerialName("Message") override val message: StaticDataReportContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : StaticDataAisMessageWrapper
