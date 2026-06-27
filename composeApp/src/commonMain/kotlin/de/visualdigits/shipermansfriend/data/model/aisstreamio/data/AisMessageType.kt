package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.MessageType
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

enum class AisMessageType(
    val messageType: MessageType,
    val subClass: KClass<out AisMessageData>,
    val serializer: KSerializer<out AisMessageData>
) {
    ADDRESSED_BINARY_MESSAGE(MessageType.AddressedBinaryMessage, AddressedBinaryMessage::class, AddressedBinaryMessage.serializer()),
    ADDRESSED_SAFETY_MESSAGE(MessageType.AddressedSafetyMessage, AddressedSafetyMessage::class, AddressedSafetyMessage.serializer()),
    AIDS_TO_NAVIGATION_REPORT(MessageType.AidsToNavigationReport, AidsToNavigationReport::class, AidsToNavigationReport.serializer()),
    ASSIGNED_MODE_COMMAND(MessageType.AssignedModeCommand, AssignedModeCommand::class, AssignedModeCommand.serializer()),
    BASE_STATION_REPORT(MessageType.BaseStationReport, BaseStationReport::class, BaseStationReport.serializer()),
    BINARY_ACKNOWLEDGE(MessageType.BinaryAcknowledge, BinaryAcknowledge::class, BinaryAcknowledge.serializer()),
    CHANNEL_MANAGEMENT(MessageType.ChannelManagement, ChannelManagement::class, ChannelManagement.serializer()),
    COORDINATED_UTC_INQUIRY(MessageType.CoordinatedUTCInquiry, CoordinatedUTCInquiry::class, CoordinatedUTCInquiry.serializer()),
    DATA_LINK_MANAGEMENT_MESSAGE(MessageType.DataLinkManagementMessage, DataLinkManagementMessage::class, DataLinkManagementMessage.serializer()),
    EXTENDED_CLASSS_BP_OSITION_REPORT(MessageType.ExtendedClassBPositionReport, ExtendedClassBPositionReport::class, ExtendedClassBPositionReport.serializer()),
    GNSS_BROADCAST_BINARY_MESSAGE(MessageType.GnssBroadcastBinaryMessage, GnssBroadcastBinaryMessage::class, GnssBroadcastBinaryMessage.serializer()),
    INTERROGATION(MessageType.Interrogation, Interrogation::class, Interrogation.serializer()),
    LONG_RANGE_AIS_BROADCAST_MESSAGE(MessageType.LongRangeAisBroadcastMessage, LongRangeAisBroadcastMessage::class, LongRangeAisBroadcastMessage.serializer()),
    MULTI_SLOT_BINARY_MESSAGE(MessageType.MultiSlotBinaryMessage, MultiSlotBinaryMessage::class, MultiSlotBinaryMessage.serializer()),
    POSITION_REPORT(MessageType.PositionReport, PositionReport::class, PositionReport.serializer()),
    SAFETY_BROADCAST_MESSAGE(MessageType.SafetyBroadcastMessage, SafetyBroadcastMessage::class, SafetyBroadcastMessage.serializer()),
    SHIP_STATIC_DATA(MessageType.ShipStaticData, ShipStaticData::class, ShipStaticData.serializer()),
    SINGLE_SLOT_BINARY_MESSAGE(MessageType.SingleSlotBinaryMessage, SingleSlotBinaryMessage::class, SingleSlotBinaryMessage.serializer()),
    STANDARD_CLASSS_B_POSITION_REPORT(MessageType.StandardClassBPositionReport, StandardClassBPositionReport::class, StandardClassBPositionReport.serializer()),
    STANDARD_SEARCH_AND_RESCUE_AIRCRAFT_REPORT(MessageType.StandardSearchAndRescueAircraftReport, StandardSearchAndRescueAircraftReport::class, StandardSearchAndRescueAircraftReport.serializer()),
    STATIC_DATA_REPORT(MessageType.StaticDataReport, StaticDataReport::class, StaticDataReport.serializer()),
    UNKNOWN_MESSAGE(MessageType.UnknownMessage, UnknownMessage::class, UnknownMessage.serializer())
    ;

    companion object {

        fun fromJsonKey(key: String?): AisMessageType? {
            return key?.let { k -> fromMessageType(MessageType.valueOf(k)) }
        }

        fun fromMessageType(messageType: MessageType): AisMessageType? {
            return entries.find { it.messageType == messageType }
        }

        fun fromSubClass(subClass: KClass<out AisMessageData>): AisMessageType? {
            return entries.find { it.subClass == subClass }
        }
    }
}
