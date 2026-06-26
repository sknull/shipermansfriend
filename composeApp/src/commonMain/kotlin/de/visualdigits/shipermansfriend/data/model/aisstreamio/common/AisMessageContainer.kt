package de.visualdigits.shipermansfriend.data.model.aisstreamio.common

interface AisMessageContainer<T : AisMessageContainer<T>> {

    val data: AisMessageData<T>
}
