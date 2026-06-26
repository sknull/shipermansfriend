package de.visualdigits.shipermansfriend.data.model.aisstreamio.shipstaticdata

import de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata.StaticDataAisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ShipStaticDataContainer(
    @SerialName("ShipStaticData") override val data: ShipStaticData
) : StaticDataAisMessageContainer<ShipStaticDataContainer> {
    override fun toString(): String {
        return data.toString()
    }
}

