package de.visualdigits.shipermansfriend.domain.model.serializer

import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object FixTypeSerializer : KSerializer<FixType> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = "FixType"
    ) {
        element<String>("FixType")
    }

    override fun serialize(
        encoder: Encoder,
        value: FixType
    ) {
        encoder.encodeString(value.ordinal.toString())
    }

    override fun deserialize(decoder: Decoder): FixType {
        return FixType.fromOrdinal(decoder.decodeInt()) ?: FixType.UNDEFINED
    }
}
