package de.visualdigits.shipermansfriend.di

import app.cash.sqldelight.ColumnAdapter
import de.visualdigits.common.domain.util.CryptoBox
import de.visualdigits.common.domain.util.EncryptedString
import de.visualdigits.shipermansfriend.SettingsDatabase
import de.visualdigits.shipermansfriend.SettingsEntity
import de.visualdigits.shipermansfriend.ShipermansFriendDatabaseQueries
import de.visualdigits.shipermansfriend.data.database.DriverFactory
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.data.repository.DefaultMasterDataRepository
import de.visualdigits.shipermansfriend.data.repository.DefaultSettingsRepository
import de.visualdigits.shipermansfriend.data.repository.VesselDataRepository
import de.visualdigits.shipermansfriend.domain.repository.MasterDataRepository
import de.visualdigits.shipermansfriend.domain.repository.SettingsRepository
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

expect val homeDirectory: String

val sharedModule = module {

    single(named("homeDirectory")) { homeDirectory }

    single { CoroutineScope(SupervisorJob() + Dispatchers.Default) }

    singleOf(::ShipermansFriendViewModel)

    single {
        val driver = get<DriverFactory>().createDriver(get<String>(named("homeDirectory")))
        val cryptoBox = get<CryptoBox>()

        val passwordAdapter = object : ColumnAdapter<EncryptedString, String> {
            override fun decode(databaseValue: String): EncryptedString = cryptoBox.decrypt(databaseValue)
            override fun encode(value: EncryptedString): String = cryptoBox.encrypt(value)
        }

        SettingsDatabase(
            driver,
            SettingsEntityAdapter = SettingsEntity.Adapter(
                aisstreamApiKeyAdapter = passwordAdapter
            )
        )
    }

    single<ShipermansFriendDatabaseQueries> {
        get<SettingsDatabase>().shipermansFriendDatabaseQueries
    }

    singleOf(::DefaultSettingsRepository).bind<SettingsRepository>()
    singleOf(::DefaultMasterDataRepository).bind<MasterDataRepository>()
    single {
        AisStreamClient(
            httpClient = get(),
            settingsRepository = get(),
            locationProvider = get(),
            scope = get()
        )
    }
    single {
        VesselDataRepository(
            httpClient = get()
        )
    }
}
