package de.visualdigits.shipermansfriend.data.model.wikidata


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WikiDataResponse(
    @SerialName("head") val head: Head,
    @SerialName("results") val results: Results
) {
    val vesselData: VesselData?
        get() = if (results.bindings.isEmpty()) null else {
            val first = results.bindings.first()

            VesselData(
                name = first.vesselLabel?.value,
                imo = first.imo?.value?.toLongOrNull(),
                mmsi = first.mmsi?.value?.toLongOrNull(),
                tonnage = first.tonnage?.value?.toDoubleOrNull(),
                length = first.length?.value?.toDoubleOrNull(),
                width = first.width?.value?.toDoubleOrNull(),
                teu = first.teu?.value?.toIntOrNull(),
                imageUrls = results.bindings.mapNotNull { it.image?.value }.distinct<String>()
            )
        }
}

