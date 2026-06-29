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

enum class MmsiPrefixReserved(
    override val prefix: String,
    val label: StringResource
) : MmsiPrefix {
    
    BASE_STATION("00", Res.string.label_BaseStation),
    GROUP_MMSI("0", Res.string.label_GroupMmsi),
    SAR_VEHICLE("111", Res.string.label_SarVehicle),
    RESCUE_TRANSMITTER("970", Res.string.label_RescueTransmitter),
    MAN_OVER_BOARD_DEVICE("972", Res.string.label_ManOverBoardDevice),
    EMERGENCY_DEVICE("974", Res.string.label_EmergencyDevice),
    SAR_DAUGHTER_BOAT("98", Res.string.label_SarDaughterBoat),
    ATON("99", Res.string.label_Aton),
    STANDARD_VESSEL("", Res.string.label_StandardVessel)
    ;

    companion object {

        fun fromNormalizedMmsi(normalizedMmsi: String): MmsiPrefixReserved {
            return MmsiPrefixReserved.entries
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
