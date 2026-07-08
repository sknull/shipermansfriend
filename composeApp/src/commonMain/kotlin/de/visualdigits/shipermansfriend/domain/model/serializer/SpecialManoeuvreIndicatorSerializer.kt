package de.visualdigits.shipermansfriend.domain.model.serializer

import de.visualdigits.shipermansfriend.domain.model.geodata.SpecialManoeuvreIndicator
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object SpecialManoeuvreIndicatorSerializer : KSerializer<SpecialManoeuvreIndicator> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = "NavigationalStatus"
    ) {
        element<String>("NavigationalStatus")
    }

    override fun serialize(
        encoder: Encoder,
        value: SpecialManoeuvreIndicator
    ) {
        encoder.encodeString(value.code.toString())
    }

    override fun deserialize(decoder: Decoder): SpecialManoeuvreIndicator {
        val code = decoder.decodeLong()
        return SpecialManoeuvreIndicator.fromCode(code)
    }
}
