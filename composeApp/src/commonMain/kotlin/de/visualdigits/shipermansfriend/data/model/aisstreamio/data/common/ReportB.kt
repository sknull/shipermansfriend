package de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common


import androidx.compose.runtime.Immutable
import de.visualdigits.shipermansfriend.domain.model.geodata.FixType
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ReportB(
    @SerialName("CallSign") val callSign: String,
    @SerialName("Dimension") val dimension: Dimension,
    @SerialName("FixType") val fixType: FixType,
    @SerialName("ShipType") val shipType: ShipType,
    @SerialName("Spare") val spare: Long,
    @SerialName("Valid") val valid: Boolean,
    @SerialName("VenderIDModel") val venderIDModel: Long,
    @SerialName("VenderIDSerial") val venderIDSerial: Long,
    @SerialName("VendorIDName") val vendorIDName: String
)
