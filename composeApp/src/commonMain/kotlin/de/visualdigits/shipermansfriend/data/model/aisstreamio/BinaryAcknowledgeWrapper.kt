package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.binaryacknowledge.BinaryAcknowledgeContainer
import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMetaData
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("BinaryAcknowledge")
class BinaryAcknowledgeWrapper(
    @SerialName("Message") override val message: BinaryAcknowledgeContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : StandardAisMessageWrapper
