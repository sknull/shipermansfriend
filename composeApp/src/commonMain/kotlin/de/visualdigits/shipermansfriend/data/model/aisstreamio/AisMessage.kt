package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.AisMessageData
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.MessageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AisMessage(
    @SerialName("Message") val data: AisMessageData,
    @SerialName("MessageType") val messageType: MessageType,
    @SerialName("MetaData") val metaData: AisMetaData
)
