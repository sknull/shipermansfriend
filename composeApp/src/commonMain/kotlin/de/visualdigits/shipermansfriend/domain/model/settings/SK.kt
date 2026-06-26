package de.visualdigits.shipermansfriend.domain.model.settings

import de.visualdigits.common.domain.model.configuration.FieldKey

enum class SK : FieldKey<SK> {

    language,
    aisstreamApiKey,
    location,
    useGpsLocation,
    radiusOuter, // used for master data tracking
    radiusInner, // used for position data tracking

    maxImageSize,
    ;

    companion object {
        fun fromString(value: String): SK? {
            return entries.find { entry -> entry.name == value }
        }
    }
}
