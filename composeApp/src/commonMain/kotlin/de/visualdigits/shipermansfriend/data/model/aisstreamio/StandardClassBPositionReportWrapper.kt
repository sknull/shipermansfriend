package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.standardclassbpositionreport.StandardClassBPositionReportContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("StandardClassBPositionReport")
class StandardClassBPositionReportWrapper(
    @SerialName("Message") override val message: StandardClassBPositionReportContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : PositionAisMessageWrapper
