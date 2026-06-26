package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.ui.graphics.Color
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.label_Cargo
import de.visualdigits.compose.resources.label_Fishing
import de.visualdigits.compose.resources.label_HSC
import de.visualdigits.compose.resources.label_Marine
import de.visualdigits.compose.resources.label_Medical
import de.visualdigits.compose.resources.label_Military
import de.visualdigits.compose.resources.label_Other
import de.visualdigits.compose.resources.label_Passenger
import de.visualdigits.compose.resources.label_Pilot
import de.visualdigits.compose.resources.label_PleasureCraft
import de.visualdigits.compose.resources.label_Police
import de.visualdigits.compose.resources.label_PortTender
import de.visualdigits.compose.resources.label_SAR
import de.visualdigits.compose.resources.label_Sailing
import de.visualdigits.compose.resources.label_Tanker
import de.visualdigits.compose.resources.label_Towing
import de.visualdigits.compose.resources.label_Tug
import de.visualdigits.compose.resources.label_Unknown
import de.visualdigits.compose.resources.label_WIG
import de.visualdigits.compose.resources.vessel_Cargo
import de.visualdigits.compose.resources.vessel_Fishing
import de.visualdigits.compose.resources.vessel_HSC
import de.visualdigits.compose.resources.vessel_Marine
import de.visualdigits.compose.resources.vessel_Medical
import de.visualdigits.compose.resources.vessel_Military
import de.visualdigits.compose.resources.vessel_Other
import de.visualdigits.compose.resources.vessel_Passenger_medium
import de.visualdigits.compose.resources.vessel_Pilot
import de.visualdigits.compose.resources.vessel_PleasureCraft
import de.visualdigits.compose.resources.vessel_Police
import de.visualdigits.compose.resources.vessel_PortTender
import de.visualdigits.compose.resources.vessel_SAR
import de.visualdigits.compose.resources.vessel_Sailing
import de.visualdigits.compose.resources.vessel_Tanker
import de.visualdigits.compose.resources.vessel_Towing
import de.visualdigits.compose.resources.vessel_Tug
import de.visualdigits.compose.resources.vessel_Unknown
import de.visualdigits.compose.resources.vessel_WIG
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class ShipCategory(
    val color: Color,
    val icon: DrawableResource,
    val label: StringResource
) {

    Cargo(
        color = Color(0xff00ff00),
        icon = Res.drawable.vessel_Cargo,
        label = Res.string.label_Cargo
    ),
    Fishing(
        color = Color(0xffff7700),
        icon = Res.drawable.vessel_Fishing,
        label = Res.string.label_Fishing
    ),
    HSC(
        color = Color(0xffffff00),
        icon = Res.drawable.vessel_HSC,
        label = Res.string.label_HSC
    ), // High Speed Craft
    Marine(
        color = Color(0xff00ffff),
        icon = Res.drawable.vessel_Marine,
        label = Res.string.label_Marine
    ),
    Medical(
        color = Color(0xffff7777),
        icon = Res.drawable.vessel_Medical,
        label = Res.string.label_Medical
    ),
    Military(
        color = Color(0xff000066),
        icon = Res.drawable.vessel_Military,
        label = Res.string.label_Military
    ),
    Other(
        color = Color(0xff888888),
        icon = Res.drawable.vessel_Other,
        label = Res.string.label_Other
    ),
    Passenger(
        color = Color(0xff0000ff),
        icon = Res.drawable.vessel_Passenger_medium,
        label = Res.string.label_Passenger
    ),
    Pilot(
        color = Color(0xff00ffff),
        icon = Res.drawable.vessel_Pilot,
        label = Res.string.label_Pilot
    ),
    PleasureCraft(
        color = Color(0xffff00ff),
        icon = Res.drawable.vessel_PleasureCraft,
        label = Res.string.label_PleasureCraft
    ),
    Police(
        color = Color(0xff7700ff),
        icon = Res.drawable.vessel_Police,
        label = Res.string.label_Police
    ),
    PortTender(
        color = Color(0xff00ffff),
        icon = Res.drawable.vessel_PortTender,
        label = Res.string.label_PortTender
    ),
    SAR(
        color = Color(0xff7700ff),
        icon = Res.drawable.vessel_SAR,
        label = Res.string.label_SAR
    ),
    Sailing(
        color = Color(0xffff00ff),
        icon = Res.drawable.vessel_Sailing,
        label = Res.string.label_Sailing
    ),
    Tanker(
        color = Color(0xffff0000),
        icon = Res.drawable.vessel_Tanker,
        label = Res.string.label_Tanker
    ),
    Towing(
        color = Color(0xff00ffff),
        icon = Res.drawable.vessel_Towing,
        label = Res.string.label_Towing
    ),
    Tug(
        color = Color(0xff00ffff),
        icon = Res.drawable.vessel_Tug,
        label = Res.string.label_Tug
    ),
    Unknown(
        color = Color(0xff888888),
        icon = Res.drawable.vessel_Unknown,
        label = Res.string.label_Unknown
    ),
    WIG(
        color = Color(0xffffff00),
        icon = Res.drawable.vessel_WIG,
        label = Res.string.label_WIG
    ) // Wing In Ground - Bodeneffekt Fahrzeug
    ;

    companion object
}
