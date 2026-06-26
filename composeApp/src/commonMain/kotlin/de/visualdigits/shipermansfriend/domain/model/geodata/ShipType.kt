package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.ShipTypeSerializer
import kotlinx.serialization.Serializable

@Serializable(with = ShipTypeSerializer::class)
enum class ShipType(
    val code: Long,
    val category: ShipCategory
) {
    Unknown_0(0, ShipCategory.Unknown),
    Unknown_1(1, ShipCategory.Unknown),
    Unknown_2(2, ShipCategory.Unknown),
    Unknown_3(3, ShipCategory.Unknown),
    Unknown_4(4, ShipCategory.Unknown),
    Unknown_5(5, ShipCategory.Unknown),
    Unknown_6(6, ShipCategory.Unknown),
    Unknown_7(7, ShipCategory.Unknown),
    Unknown_8(8, ShipCategory.Unknown),
    Unknown_9(9, ShipCategory.Unknown),

    WIG_0(20, ShipCategory.WIG),
    WIG_1(21, ShipCategory.WIG),
    WIG_2(22, ShipCategory.WIG),
    WIG_3(23, ShipCategory.WIG),
    WIG_4(24, ShipCategory.WIG),
    WIG_5(25, ShipCategory.WIG),
    WIG_6(26, ShipCategory.WIG),
    WIG_7(27, ShipCategory.WIG),
    WIG_8(28, ShipCategory.WIG),
    WIG_9(29, ShipCategory.WIG),

    Fishing(30, ShipCategory.Fishing),
    Towing(31, ShipCategory.Towing),
    Towing_Big(32, ShipCategory.Towing),
    Engaged_in_dredging_or_underwater_operations(33, ShipCategory.Marine),
    Engaged_in_diving_operations(34, ShipCategory.Marine),
    Engaged_in_military_operations(35, ShipCategory.Military),
    Sailing(36, ShipCategory.Sailing),
    Pleasure_craft(37, ShipCategory.PleasureCraft),

    HSC_0(40, ShipCategory.HSC),
    HSC_1(41, ShipCategory.HSC),
    HSC_2(42, ShipCategory.HSC),
    HSC_3(43, ShipCategory.HSC),
    HSC_4(44, ShipCategory.HSC),
    HSC_5(45, ShipCategory.HSC),
    HSC_6(46, ShipCategory.HSC),
    HSC_7(47, ShipCategory.HSC),
    HSC_8(48, ShipCategory.HSC),
    HSC_9(49, ShipCategory.HSC),

    Pilot_vessel(50, ShipCategory.Pilot),
    Search_and_rescue_vessels(51, ShipCategory.SAR),
    Tugs(52, ShipCategory.Tug),
    Port_tenders(53, ShipCategory.PortTender),
    Vessels_with_anti_pollution_facilities_or_equipment(54, ShipCategory.Other),
    Law_enforcement_vessels(55, ShipCategory.Police),
    Spare_for_assignments_to_local_vessels_1(56, ShipCategory.Other),
    Spare_for_assignments_to_local_vessels_2(57, ShipCategory.Other),
    Medical_transports(58, ShipCategory.Medical),
    Ships_and_aircraft_of_States_not_parties_to_an_armed_conflict(59, ShipCategory.Military),

    Passenger_Ship_0(60, ShipCategory.Passenger),
    Passenger_Ship_1(61, ShipCategory.Passenger),
    Passenger_Ship_2(62, ShipCategory.Passenger),
    Passenger_Ship_3(63, ShipCategory.Passenger),
    Passenger_Ship_4(64, ShipCategory.Passenger),
    Passenger_Ship_5(65, ShipCategory.Passenger),
    Passenger_Ship_6(66, ShipCategory.Passenger),
    Passenger_Ship_7(67, ShipCategory.Passenger),
    Passenger_Ship_8(68, ShipCategory.Passenger),
    Passenger_Ship_9(69, ShipCategory.Passenger),

    Cargo_0(70, ShipCategory.Cargo),
    Cargo_DG_HS_MP_Cat_X(71, ShipCategory.Cargo),
    Cargo_DG_HS_MP_Cat_Y(72, ShipCategory.Cargo),
    Cargo_DG_HS_MP_Cat_Z(73, ShipCategory.Cargo),
    Cargo_DG_HS_MP_Cat_OS(74, ShipCategory.Cargo),
    Cargo_5(75, ShipCategory.Cargo),
    Cargo_6(76, ShipCategory.Cargo),
    Cargo_7(77, ShipCategory.Cargo),
    Cargo_8(78, ShipCategory.Cargo),
    Cargo_9(79, ShipCategory.Cargo),

    Tanker_0(80, ShipCategory.Tanker),
    Tanker_DG_HS_MP_Cat_X(81, ShipCategory.Tanker),
    Tanker_DG_HS_MP_Cat_Y(82, ShipCategory.Tanker),
    Tanker_DG_HS_MP_Cat_Z(83, ShipCategory.Tanker),
    Tanker_DG_HS_MP_Cat_OS(84, ShipCategory.Tanker),
    Tanker_5(85, ShipCategory.Tanker),
    Tanker_6(86, ShipCategory.Tanker),
    Tanker_7(87, ShipCategory.Tanker),
    Tanker_8(88, ShipCategory.Tanker),
    Tanker_9(89, ShipCategory.Tanker),

    Other_0(90, ShipCategory.Other),
    Other_1(91, ShipCategory.Other),
    Other_2(92, ShipCategory.Other),
    Other_3(93, ShipCategory.Other),
    Other_4(94, ShipCategory.Other),
    Other_5(95, ShipCategory.Other),
    Other_6(96, ShipCategory.Other),
    Other_7(97, ShipCategory.Other),
    Other_8(98, ShipCategory.Other),
    Other_9(99, ShipCategory.Other)
    ;

    companion object {
        fun fromValue(code: Long): ShipType? = entries.find { it.code == code }
    }
}
