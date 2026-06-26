package de.visualdigits.shipermansfriend.data.model.aisstreamio.interrogation

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class InterrogationContainer(
    @SerialName("Interrogation") override val data: Interrogation
) : AisMessageContainer<InterrogationContainer> {
    override fun toString(): String {
        return data.toString()
    }
}

