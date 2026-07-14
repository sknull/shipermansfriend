package de.visualdigits.shipermansfriend.domain.model.geodata

import de.visualdigits.compose.resources.Res
import de.visualdigits.compose.resources.icon_anchor_24px
import de.visualdigits.compose.resources.icon_input_24px
import de.visualdigits.compose.resources.icon_output_24px
import de.visualdigits.compose.resources.icon_question_mark_24px
import de.visualdigits.compose.resources.icon_stop_circle_24px
import de.visualdigits.compose.resources.label_Unknown
import de.visualdigits.compose.resources.label_inbound
import de.visualdigits.compose.resources.label_moored
import de.visualdigits.compose.resources.label_outbound
import de.visualdigits.compose.resources.label_stationary
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class MovementDirection(
    val label: StringResource,
    val icon: DrawableResource
) {
    INBOUND(Res.string.label_inbound,Res.drawable.icon_input_24px),
    OUTBOUND(Res.string.label_outbound,Res.drawable.icon_output_24px),
    MOORED(Res.string.label_moored,Res.drawable.icon_anchor_24px),
    UNKNOWN(Res.string.label_Unknown,Res.drawable.icon_question_mark_24px)
}
