package de.visualdigits.shipermansfriend.data.model.aisstreamio.staticdata

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageContainer

interface StaticDataAisMessageContainer<T : StaticDataAisMessageContainer<T>> : AisMessageContainer<T> {

    override val data: StaticDataAisMessageData<T>
}
