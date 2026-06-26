package de.visualdigits.shipermansfriend.data.model.wikidata

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VesselBinding(
    @SerialName("vesselLabel") val vesselLabel: BindingValue? = null,
    @SerialName("imo") val imo: BindingValue? = null,
    @SerialName("mmsi") val mmsi: BindingValue? = null,
    @SerialName("tonnage") val tonnage: BindingValue? = null,
    @SerialName("length") val length: BindingValue? = null,
    @SerialName("width") val width: BindingValue? = null,
    @SerialName("teu") val teu: BindingValue? = null,
    @SerialName("image") val image: BindingValue? = null
)
