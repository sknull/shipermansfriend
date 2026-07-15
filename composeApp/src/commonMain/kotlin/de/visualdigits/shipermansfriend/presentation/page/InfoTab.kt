package de.visualdigits.shipermansfriend.presentation.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import be.digitalia.compose.htmlconverter.HtmlStyle
import be.digitalia.compose.htmlconverter.htmlToAnnotatedString
import de.visualdigits.common.domain.model.common.KmpOffsetDateTime
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.common.presentation.components.PlatformVerticalScrollbarBox
import de.visualdigits.common.presentation.components.util.conditional
import de.visualdigits.common.presentation.model.PlatformScrollbarStyle
import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.Shipermans_Banner
import de.visualdigits.compose.resources.icon_music_cast_24px
import de.visualdigits.compose.resources.icon_music_note_2_24px
import de.visualdigits.compose.resources.title_anthems
import de.visualdigits.generated.AppVersion
import de.visualdigits.shipermansfriend.di.AudioStorage
import de.visualdigits.shipermansfriend.domain.model.geodata.unlocode.Country
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendAction
import de.visualdigits.shipermansfriend.presentation.model.ShipermansFriendState
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.SandYellow
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import de.visualdigits.shipermansfriend.presentation.util.routePlatformLink
import eu.iamkonstantin.kotlin.gadulka.GadulkaPlayer
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun InfoTab(
    state: ShipermansFriendState,
    platformType: PlatformType,
    player: GadulkaPlayer,
    audioStorage: AudioStorage,
    onAction: (ShipermansFriendAction) -> Unit
) {

    var anthems by remember { mutableStateOf<List<Pair<Country, String>>>(emptyList()) }
    LaunchedEffect(Unit) {
        anthems = Country.entries.mapNotNull { country ->
            val uri = audioStorage.prepareAudio("${country.prefix}.mp3")
            if (uri != null) {
                Pair(country, uri)
            } else {
                null
            }
        }
    }
    val interactionSource = remember { MutableInteractionSource() }

    PlatformVerticalScrollbarBox(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = if (platformType == PlatformType.jvm) 20.dp else 0.dp),
        platformType = platformType,
        scrollbarModifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .width(10.dp)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)),
        scrollbarStyle = PlatformScrollbarStyle(
            minimalHeight = 16.dp,
            thickness = 8.dp,
            shape = RoundedCornerShape(4.dp),
            hoverDurationMillis = 300,
            unhoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
            hoverColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        ),
    ) {
        listOf(Pair("info", @Composable {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceContainer, MaterialTheme.shapes.small)
                    .padding(MaterialTheme.shapes.gap),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
            ) {
                val linkColor = MaterialTheme.colorScheme.onSurface

                Image(
                    modifier = Modifier
                        .fillMaxWidth(),
                    painter = painterResource(Res.drawable.Shipermans_Banner),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                )

                Text(
                    text = remember(linkColor) {
                        htmlToAnnotatedString(
                            html = """
                            <h1>News Home Reader</h1>
                            <h3>Version ${AppVersion().version}</h3>
                            <br/>
                            <div>© ${KmpOffsetDateTime.now().year} by <a href=\"mailto:s.knull@t-online.de\">Stephan Knull</a>.<div>
                            <div>Github <a href=\"https://github.com/sknull\">My GitHub</a>.<div>
                            """.trimIndent(),
                            style = HtmlStyle(
                                textLinkStyles = TextLinkStyles(style = SpanStyle(color = linkColor)),
                                isTextColorEnabled = true
                            ),
                            linkInteractionListener = { linkAnnotation -> routePlatformLink((linkAnnotation as LinkAnnotation.Url).url) }
                        )
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(50.dp))

                Text(
                    text = stringResource(Res.string.title_anthems),
                    style = MaterialTheme.typography.titleMedium
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap)
                ) {
                    anthems
                        .sortedBy { it.first.countryName }
                        .forEach { (country, uri) ->
                            Row(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.extraSmall)
                                    .width(150.dp)
                                    .height(50.dp)
                                    .conditional(state.playingAnthem != country.prefix) { background(MarineBlueLight) }
                                    .conditional(state.playingAnthem == country.prefix) { background(SandYellow) }
                                    .padding(MaterialTheme.shapes.gap).pointerHoverIcon(PointerIcon.Hand)
                                    .hoverable(interactionSource)
                                    .clickable {
                                        if (state.playingAnthem != country.prefix) {
                                            onAction(ShipermansFriendAction.OnPlayAnthem(country.prefix))
                                            player.play(uri)
                                        } else {
                                            onAction(ShipermansFriendAction.OnPlayAnthem(null))
                                            player.stop()
                                        }
                                    },
                                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap, Alignment.CenterHorizontally),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap / 2),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .height(25.dp),
                                        painter = painterResource(country.flag),
                                        contentDescription = country.countryName,
                                        contentScale = ContentScale.Fit,
                                    )
                                    Text(
                                        text = country.countryName,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
                                        color = if (state.playingAnthem == country.prefix) TextColor else Color.White
                                    )
                                }

                                if (state.playingAnthem == country.prefix) {
                                    Icon(
                                        painter = painterResource(Res.drawable.icon_music_note_2_24px),
                                        contentDescription = null,
                                        tint = TextColor
                                    )
                                }
                            }
                        }
                }
            }
        }))
    }
}
