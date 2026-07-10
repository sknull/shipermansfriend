package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Dimension
import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Eta
import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ShipStaticData(
    @SerialName("AisVersion") val aisVersion: Long,
    @SerialName("CallSign") override val callSign: String,
    @SerialName("Destination") override val destination: String,
    @SerialName("Dimension") override val dimension: Dimension,
    @SerialName("Dte") val dte: Boolean,
    @SerialName("Eta") val eta: Eta,
    @SerialName("FixType") val fixType: FixType,
    @SerialName("ImoNumber") override val imoNumber: Long,
    @SerialName("MaximumStaticDraught") override val maximumStaticDraught: Double,
    @SerialName("MessageID") val messageId: Long,
    @SerialName("Name") val name: String,
    @SerialName("RepeatIndicator") val repeatIndicator: Long,
    @SerialName("Spare") val spare: Boolean,
    @SerialName("Type") val type: ShipType,
    @SerialName("UserID") val mmsi: Long,
    @SerialName("Valid") override val valid: Boolean
) : StaticDataAisMessageData {

    override val shipType: ShipType
        get() = type
}
