package de.visualdigits.shipermansfriend.domain.model.serializer

import de.visualdigits.shipermansfriend.domain.model.geodata.NavigationalStatus
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object NavigationalStatusSerializer : KSerializer<NavigationalStatus> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = "NavigationalStatus"
    ) {
        element<String>("NavigationalStatus")
    }

    override fun serialize(
        encoder: Encoder,
        value: NavigationalStatus
    ) {
        encoder.encodeString(value.code.toString())
    }

    override fun deserialize(decoder: Decoder): NavigationalStatus {
        val code = decoder.decodeLong()
        return NavigationalStatus.fromCode(code)
    }
}
