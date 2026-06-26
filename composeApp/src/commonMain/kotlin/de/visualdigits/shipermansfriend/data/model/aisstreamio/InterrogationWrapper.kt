package de.visualdigits.shipermansfriend.data.model.aisstreamio

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMetaData
import de.visualdigits.shipermansfriend.data.model.aisstreamio.interrogation.InterrogationContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("Interrogation")
class InterrogationWrapper(
    @SerialName("Message") override val message: InterrogationContainer,
    @SerialName("MetaData") override val metaData: AisMetaData
) : StandardAisMessageWrapper
