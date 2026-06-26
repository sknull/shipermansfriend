package de.visualdigits.shipermansfriend

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import com.formdev.flatlaf.FlatDarculaLaf
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.domain.service.getPlatformLogWriters
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.favicon
import de.visualdigits.shipermansfriend.di.platformModule
import de.visualdigits.shipermansfriend.di.sharedModule
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.withContext
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import java.awt.Window
import javax.swing.SwingUtilities
import javax.swing.UIManager

fun main() {

//    java.util.logging.Logger.getLogger("okhttp3.OkHttpClient").level = Level.FINE

    val koinApp = startKoin {
        modules(sharedModule, platformModule)
    }
    val viewModel: ShipermansFriendViewModel = koinApp.koin.get()
    val homeDirectoryPath = koinApp.koin.get<String>(named("homeDirectory"))

    val writers = getPlatformLogWriters(homeDirectoryPath, "ShipermansFriend.log")
    Logger.setLogWriters(writers)
    Logger.setTag("ShipermansFriend")
    Logger.setMinSeverity(Severity.Debug)

    System.setProperty("flatlaf.useWindowDecorations", "true")

    application {
        val ioScope = rememberCoroutineScope()
        val state = rememberWindowState(
            width = 1200.dp,
            height = 900.dp,
            position = WindowPosition(Alignment.Center)
        )

        LaunchedEffect(viewModel) {
            viewModel.state.collect { state ->
                withContext(Dispatchers.Main) {
                    try {
                        UIManager.setLookAndFeel(FlatDarculaLaf())
                        SwingUtilities.invokeLater {
                            for (window in Window.getWindows()) {
                                SwingUtilities.updateComponentTreeUI(window)
                            }
                        }
                    } catch (e: Exception) {
                        Logger.e("Laf konnte nicht gesetzt werden", e)
                    }
                }
            }
        }

        Window(
            onCloseRequest = {
                ioScope.cancel("Normal Exit")
                exitApplication()
            },
            title = "Shiperman's Friend",
            icon = painterResource(Res.drawable.favicon),
            state = state
        ) {
            App(PlatformType.jvm)
        }
    }
}
