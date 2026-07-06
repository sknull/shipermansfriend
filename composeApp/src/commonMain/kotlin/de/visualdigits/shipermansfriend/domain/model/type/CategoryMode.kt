package de.visualdigits.shipermansfriend.domain.model.type

import androidx.compose.ui.graphics.Color

enum class CategoryMode(
    val color: Color
) {

    solo(Color(0xFFFFFFFF)),
    mute(Color(0xFF000000)),
    unselected(Color(0xFF888888))
    ;
}
