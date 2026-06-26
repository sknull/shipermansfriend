package de.visualdigits.shipermansfriend.data.model.wikidata

data class VesselData(
    val name: String? = null,
    val imo: Long? = null,
    val mmsi: Long? = null,
    val tonnage: Double? = null,
    val length: Double? = null,
    val width: Double? = null,
    val teu: Int? = null,
    val imageUrls: List<String> = listOf()
)
