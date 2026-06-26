package de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageContainer
import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.Dimension
import de.visualdigits.shipermansfriend.domain.model.geodata.ShipType

interface StaticDataAisMessageData<C : AisMessageContainer<C>> : AisMessageData<C> {

    val valid: Boolean
    val imoNumber: Long
    val callSign: String
    val destination: String
    val dimension: Dimension
    val shipType: ShipType
    val maximumStaticDraught: Double
}
