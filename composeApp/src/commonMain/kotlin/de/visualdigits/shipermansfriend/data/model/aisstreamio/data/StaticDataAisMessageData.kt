package de.visualdigits.shipermansfriend.data.model.aisstreamio.data

import de.visualdigits.shipermansfriend.data.model.aisstreamio.data.common.Dimension
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType
import kotlinx.serialization.Serializable

@Serializable
sealed interface StaticDataAisMessageData : AisMessageData {

    val valid: Boolean
    val imoNumber: Long
    val callSign: String
    val destination: String
    val dimension: Dimension
    val shipType: ShipType
    val maximumStaticDraught: Double
}
