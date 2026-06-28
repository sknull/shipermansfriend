package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReportB(
    @SerialName("CallSign") val callSign: String,
    @SerialName("Dimension") val dimension: Dimension,
    @SerialName("FixType") val fixType: FixType,
    @SerialName("ShipType") val shipType: ShipType,
    @SerialName("Spare") val spare: Int,
    @SerialName("Valid") val valid: Boolean,
    @SerialName("VenderIDModel") val venderIDModel: Int,
    @SerialName("VenderIDSerial") val venderIDSerial: Int,
    @SerialName("VendorIDName") val vendorIDName: String
)
