package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.unknown.UnknownMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("UnknownMessage")
class UnknownMessageWrapper(
    @SerialName("Message") override val message: UnknownMessageContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) :  StandardAisMessageWrapper
