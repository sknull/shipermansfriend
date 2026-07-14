package de.visualdigits.shipermansfriend.domain.model.geodata

import androidx.compose.ui.graphics.Color
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLight
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLighter
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import org.jetbrains.compose.resources.StringResource

data class DataFieldDescriptor(
    val label: StringResource,
    val value: FieldValue,
    val href: String? = null,
    val wholeRow: Boolean = false,
    val textColor: Color = TextColor,
    val linkColor: Color = Color.White,
    val backgroundColor: Color = MarineBlueLighter,
    val backgroundColorLink: Color = MarineBlueLight,
)
