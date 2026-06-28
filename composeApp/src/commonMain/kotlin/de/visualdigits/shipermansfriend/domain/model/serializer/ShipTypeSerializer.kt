package de.visualdigits.shipermansfriend.domain.model.serializer

import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ShipTypeSerializer : KSerializer<ShipType> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        serialName = "ShipType"
    ) {
        element<String>("ShipType")
    }

    override fun serialize(
        encoder: Encoder,
        value: ShipType
    ) {
        encoder.encodeString(value.code.toString())
    }

    override fun deserialize(decoder: Decoder): ShipType {
        val code = decoder.decodeLong()
        return ShipType.fromValue(code) ?: ShipType.Unknown_0
    }
}
