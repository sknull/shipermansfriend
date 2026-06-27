package de.visualdigits.shipermansfriend.data.model.aisstreamio.serializer

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.AisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.AisMessageType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonObject

object AisMessageDataUnwrappingSerializer : KSerializer<AisMessageData> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("AisMessageData")

    override fun deserialize(decoder: Decoder): AisMessageData {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("This serializer supports only JSON")
        val fullMessageObject = jsonDecoder.decodeJsonElement().jsonObject
        val messageTypeKey = fullMessageObject.keys.firstOrNull()
            ?: error("The message object is empty")
        val innerContent = fullMessageObject[messageTypeKey]
            ?: error("No content found for data type '$messageTypeKey'")
        val delegateSerializer = AisMessageType.fromJsonKey(messageTypeKey)
            ?.serializer
            ?: error("Unknown data type: '$messageTypeKey'")

        return jsonDecoder.json.decodeFromJsonElement(delegateSerializer, innerContent)
    }

    @Suppress("UNCHECKED_CAST")
    override fun serialize(encoder: Encoder, value: AisMessageData) {
        val jsonEncoder = encoder as? kotlinx.serialization.json.JsonEncoder
            ?: error("This serializer supports only JSON")
        val messageType = AisMessageType.fromSubClass(value::class)
            ?: error("Unknown data type: '${value::class}'")
        val delegateSerializer = messageType.serializer as KSerializer<Any>
        val element = jsonEncoder.json.encodeToJsonElement(delegateSerializer, value)
        val wrappedObject = kotlinx.serialization.json.buildJsonObject {
            put(messageType.messageType.name, element)
        }

        jsonEncoder.encodeJsonElement(wrappedObject)
    }
}
