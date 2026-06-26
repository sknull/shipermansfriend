package de.visualdigits.shipermansfriend.data.model.aisstreamio.position

import de.visualdigits.shipermansfriend.data.model.aisstreamio.common.AisMessageContainer

interface PositionAisMessageContainer<T : PositionAisMessageContainer<T>> : AisMessageContainer<T> {

    override val data: PositionAisMessageData<T>
}
