package de.visualdigits.shipermansfriend

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import coil3.compose.setSingletonImageLoaderFactory
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.shipermansfriend.data.repository.AisStreamClient
import de.visualdigits.shipermansfriend.data.repository.ImageCache
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import de.visualdigits.shipermansfriend.presentation.page.MainPage
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    platformType: PlatformType
) {
    val viewModel = koinViewModel<ShipermansFriendViewModel>()
    val imageCache = koinInject<ImageCache>()
    val aisStreamClient = koinInject<AisStreamClient>()

    setSingletonImageLoaderFactory { _ ->
        imageCache.getImageLoader()
    }

    LaunchedEffect(Unit) {
        viewModel.platformType = platformType
    }

    MainPage(
        viewModel = viewModel,
        platformType = platformType,
        aisStreamClient = aisStreamClient
    )
}
