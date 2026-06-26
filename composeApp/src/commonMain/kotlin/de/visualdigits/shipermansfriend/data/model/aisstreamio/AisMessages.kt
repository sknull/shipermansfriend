package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageContainer
import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.position.PositionAisMessageContainer
import de.visualdigits.shipermansfriend.data.model.aisstreamio.position.PositionAisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata.StaticDataAisMessageContainer
import de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata.StaticDataAisMessageData
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("MessageType")
sealed interface AisMessageWrapper {

    @SerialName("Message") val message: AisMessageContainer<*>
    @SerialName("MetaData") val metaData: AisMetaData
    val data: AisMessageData<*>
        get() = message.data
}

sealed interface PositionAisMessageWrapper : AisMessageWrapper {

    override val message: PositionAisMessageContainer<*>
    override val metaData: AisMetaData
    override val data: PositionAisMessageData<*>
        get() = message.data
}

sealed interface StaticDataAisMessageWrapper : AisMessageWrapper {

    override val message: StaticDataAisMessageContainer<*>
    override val metaData: AisMetaData
    override val data: StaticDataAisMessageData<*>
        get() = message.data
}

sealed interface StandardAisMessageWrapper : AisMessageWrapper
