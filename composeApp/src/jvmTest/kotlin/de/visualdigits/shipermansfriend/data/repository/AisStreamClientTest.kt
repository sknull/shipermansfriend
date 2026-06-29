package de.visualdigits.shipermansfriend.data.repository

import de.visualdigits.common.domain.model.geodata.Location
import de.visualdigits.shipermansfriend.domain.util.formatDistance
import de.visualdigits.shipermansfriend.di.platformModule
import de.visualdigits.shipermansfriend.di.sharedModule
import de.visualdigits.shipermansfriend.domain.model.aisstreamio.ApiKey
import de.visualdigits.shipermansfriend.domain.model.geodata.MasterData
import de.visualdigits.shipermansfriend.domain.model.geodata.PositionData
import de.visualdigits.shipermansfriend.domain.repository.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.junit5.KoinTestExtension
import java.io.File
import kotlin.time.Duration.Companion.seconds

@Disabled("Only for local testing")
class AisStreamClientTest : KoinTest {

    private val vesselDataRepository: VesselDataRepository by inject()
    private val aisStreamClient: AisStreamClient by inject()
    private val locationProvider: LocationProvider by inject()

    @JvmField
    @RegisterExtension
    val koinTestExtension = KoinTestExtension.create {
        modules(sharedModule, platformModule)
    }

    @Test
    fun testServiceStatus() = runTest {
        val status  = withContext(Dispatchers.IO) {
            aisStreamClient.serviceStatus()
        }
        println(status)
    }

    @Test
    fun testVesselData() {
        runBlocking {
            val result = vesselDataRepository.fetchVesselDataByMmsi(imo = 9783576)
            val vesselData = result.vesselData
            println(vesselData)
        }
    }

    private val masterData: MutableMap<Long, MasterData> = mutableMapOf()
    private val positionData = MutableStateFlow<Map<Long, PositionData>>(emptyMap())
    private val location = MutableStateFlow<Location?>(null)

    @Test
    fun testClient() {
        val apiKey = File("E:\\restoreworkstation\\_Serial\\aisstream_apikey.txt").readText().trim()
        runBlocking {
            launch {
                val loc = Location(
                    latitude = 53.545977,
                    longitude = 9.9680454
                )
                location.value = loc
                val boundingBox = loc.calculateBoundingBox(radiusInMeters = 5000.0)
                val apiKey = ApiKey(
                    apiKey = apiKey,
                    boundingBoxes = boundingBox.toList()
                )
                aisStreamClient.start(apiKey)
            }

            launch {
                aisStreamClient.messages
                    .collect { message ->
                        when (message) {
                            is MasterData -> {
                                masterData[message.mmsi] = message
                            }
                            is PositionData -> {
                                positionData.update { current -> current + (message.mmsi to message) }
                            }
                        }
                }
            }

            launch {
                aisStreamClient.messages
                    .debounce(1.seconds)
                    .collect { message ->
                        when (message) {
                            is PositionData -> {
                                print("\nMoored Ships:\n- ")
                                println(positionData.value.values
                                    .filter { pd -> pd.sog < 0.5 }
                                    .sortedBy { message -> message.name.lowercase() }
                                    .joinToString("\n- ") { message ->
                                        val distance = message.location.distanceTo(location.value!!).formatDistance()
                                        "$message, distance=$distance" })
                                print("\nDriving Ships:\n- ")
                                println(positionData.value.values
                                    .filter { pd -> pd.sog > 0.5 }
                                    .sortedBy { message -> message.name.lowercase() }
                                    .joinToString("\n- ") { message ->
                                        val distance = message.location.distanceTo(location.value!!).formatDistance()
                                        "$message, distance=$distance" })
                            }
                            else -> {}
                        }
                }
            }
        }
    }
}
