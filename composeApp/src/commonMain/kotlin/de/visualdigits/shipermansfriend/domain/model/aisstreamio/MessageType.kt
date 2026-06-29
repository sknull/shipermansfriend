package de.visualdigits.shipermansfriend.domain.model.aisstreamio

enum class MessageType {

    AddressedBinaryMessage,
    AddressedSafetyMessage,
    AidsToNavigationReport,
    AssignedModeCommand,
    BaseStationReport,
    BinaryAcknowledge,
    ChannelManagement,
    CoordinatedUTCInquiry,
    DataLinkManagementMessage,
    ExtendedClassBPositionReport,
    GnssBroadcastBinaryMessage,
    Interrogation,
    LongRangeAisBroadcastMessage,
    MultiSlotBinaryMessage,
    PositionReport,
    SafetyBroadcastMessage,
    ShipStaticData,
    SingleSlotBinaryMessage,
    StandardClassBPositionReport,
    StandardSearchAndRescueAircraftReport,
    StaticDataReport,
    UnknownMessage
    ;

    companion object {

        val POSITION_DATA = listOf(
            AidsToNavigationReport,
            BaseStationReport,
            ExtendedClassBPositionReport,
            GnssBroadcastBinaryMessage,
            LongRangeAisBroadcastMessage,
            PositionReport,
            StandardClassBPositionReport,
            StandardSearchAndRescueAircraftReport
        )

        val MASTER_DATA = listOf(
            ShipStaticData,
            StaticDataReport
        )

        val SAFETY_DATA = listOf(
            AddressedSafetyMessage,
            SafetyBroadcastMessage
        )

        val MESSSAGES_OF_INTEREST = POSITION_DATA + MASTER_DATA + SAFETY_DATA
    }
}
