package de.visualdigits.shipermansfriend.data.model.aisstreamio

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
}
