package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_Aton
import de.visualdigits.compose.resources.label_BaseStation
import de.visualdigits.compose.resources.label_EmergencyDevice
import de.visualdigits.compose.resources.label_GroupMmsi
import de.visualdigits.compose.resources.label_ManOverBoardDevice
import de.visualdigits.compose.resources.label_RescueTransmitter
import de.visualdigits.compose.resources.label_SarDaughterBoat
import de.visualdigits.compose.resources.label_SarVehicle
import de.visualdigits.compose.resources.label_StandardVessel
import org.jetbrains.compose.resources.StringResource

enum class MmsiDeviceType(
    override val prefix: String,
    val label: StringResource,
    val isCritical: Boolean
) : MmsiPrefix {
    
    BASE_STATION("00", Res.string.label_BaseStation, false),
    GROUP_MMSI("0", Res.string.label_GroupMmsi, false),
    SAR_VEHICLE("111", Res.string.label_SarVehicle, false),
    RESCUE_TRANSMITTER("970", Res.string.label_RescueTransmitter, true),
    MAN_OVER_BOARD_DEVICE("972", Res.string.label_ManOverBoardDevice, true),
    EMERGENCY_DEVICE("974", Res.string.label_EmergencyDevice, true),
    SAR_DAUGHTER_BOAT("98", Res.string.label_SarDaughterBoat, false),
    ATON("99", Res.string.label_Aton, false),
    STANDARD_VESSEL("", Res.string.label_StandardVessel, false)
    ;

    companion object {

        fun fromNormalizedMmsi(normalizedMmsi: String): MmsiDeviceType {
            return MmsiDeviceType.entries
                .sortedByDescending { prefix -> prefix.prefix.length }
                .find { prefix -> normalizedMmsi.startsWith(prefix.prefix) }
                ?:STANDARD_VESSEL
        }
    }

    fun extractMid(normalizedMmsi: String): String? {
        if (normalizedMmsi.length < 9) return null

        return when (this) {
            BASE_STATION -> normalizedMmsi.substring(2, 5)
            GROUP_MMSI -> normalizedMmsi.substring(1, 4)
            SAR_VEHICLE -> normalizedMmsi.substring(3, 6)
            STANDARD_VESSEL -> normalizedMmsi.substring(0, 3)
            else -> null
        }
    }
}
