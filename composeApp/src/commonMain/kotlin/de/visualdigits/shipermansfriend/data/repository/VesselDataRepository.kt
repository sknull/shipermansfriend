package de.visualdigits.shipermansfriend.data.repository

import de.visualdigits.shipermansfriend.data.model.wikidata.WikiDataResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class VesselDataRepository(
    private val httpClient: HttpClient,
) {

    companion object {

        private val aisJson = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = true
        }

        private val HOST_URL = "https://query.wikidata.org/sparql"
    }

    suspend fun fetchVesselDataByMmsi(mmsi: Long? = null, imo: Long? = null): WikiDataResponse {
        val query = """
              SELECT ?vessel ?vesselLabel ?imo ?mmsi ?tonnage ?length ?width ?teu WHERE {
                  { ?vessel wdt:P587 "${mmsi?:""}" . }
                  UNION
                  { ?vessel wdt:P458 "${imo?:""}" . }
                  
                  OPTIONAL { ?vessel wdt:P458 ?imo . }
                  OPTIONAL { ?vessel wdt:P587 ?mmsi . }
                  OPTIONAL { ?vessel wdt:P1093 ?tonnage . }
                  OPTIONAL { ?vessel wdt:P2043 ?length . }
                  OPTIONAL { ?vessel wdt:P2261 ?width . }
                  OPTIONAL { ?vessel wdt:P1083 ?teu . } 
                  OPTIONAL { ?vessel wdt:P18 ?image . }
                   
                  SERVICE wikibase:label { bd:serviceParam wikibase:language "de,en". }
              }
        """.trimIndent()

        // Absenden des HTTP GET Requests an Wikidata
        val response: HttpResponse = httpClient.get(HOST_URL) {
            parameter("query", query)
            parameter("format", "json")
        }

        val json = response.bodyAsText()
        return aisJson.decodeFromString(WikiDataResponse.serializer(), json)
    }}
