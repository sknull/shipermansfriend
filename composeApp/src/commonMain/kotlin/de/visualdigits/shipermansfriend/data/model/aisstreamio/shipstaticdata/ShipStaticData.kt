package de.visualdigits.shipermansfriend.data.model.aisstreamio.shipstaticdata


import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.Dimension
import de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata.StaticDataAisMessageData
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShipStaticData(
    @SerialName("AisVersion") val aisVersion: Int,
    @SerialName("CallSign") override val callSign: String,
    @SerialName("Destination") override val destination: String,
    @SerialName("Dimension") override val dimension: Dimension,
    @SerialName("Dte") val dte: Boolean,
    @SerialName("Eta") val eta: Eta,
    @SerialName("FixType") val fixType: Int,
    @SerialName("ImoNumber") override val imoNumber: Long,
    @SerialName("MaximumStaticDraught") override val maximumStaticDraught: Double,
    @SerialName("MessageID") val messageID: Int,
    @SerialName("Name") val name: String,
    @SerialName("RepeatIndicator") val repeatIndicator: Int,
    @SerialName("Spare") val spare: Boolean,
    @SerialName("Type") val type: ShipType,
    @SerialName("UserID") val userID: Int,
    @SerialName("Valid") override val valid: Boolean
) : StaticDataAisMessageData<ShipStaticDataContainer> {

    override val shipType: ShipType
        get() = type

    override fun toString(): String {
        return "Ship static data: IMO=${imoNumber} callsign=${callSign.trim()} type=${type.category.name} maxDraught=${maximumStaticDraught} destinated to ${destination.trim()}, ETA is ${eta}."
    }
}
