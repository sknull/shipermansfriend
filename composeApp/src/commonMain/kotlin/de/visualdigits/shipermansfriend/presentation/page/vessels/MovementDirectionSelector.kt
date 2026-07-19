package de.visualdigits.shipermansfriend.presentation.page.vessels

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import de.visualdigits.shipermansfriend.domain.model.geodata.MovementDirection
import de.visualdigits.shipermansfriend.presentation.style.MarineBlueLighter
import de.visualdigits.shipermansfriend.presentation.style.TextColor
import de.visualdigits.shipermansfriend.presentation.style.gap
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovementDirectionSelector(
    expanded: Boolean,
    movementDirections: List<MovementDirection>,
    currentMovementDirection: MovementDirection,
    setExpanded: (Boolean) -> Unit,
    setCurrentMovementDirection: (MovementDirection) -> Unit,
) {
    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        expanded = expanded,
        onExpandedChange = { setExpanded(!expanded) },
    ) {
        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.extraSmall)
                .border(1.dp, TextColor, MaterialTheme.shapes.extraSmall)
                .pointerHoverIcon(PointerIcon.Hand)
                .fillMaxWidth()
                .background(MarineBlueLighter)
                .height(30.dp)
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .exposedDropdownSize()
                .padding(MaterialTheme.shapes.gap / 2),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = stringResource(currentMovementDirection.label)
            Icon(
                modifier = Modifier
                    .height(24.dp),
                painter = painterResource(currentMovementDirection.icon),
                contentDescription = text,
                tint = TextColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.weight(1f))
            ExposedDropdownMenuDefaults.TrailingIcon(
                modifier = Modifier
                    .fillMaxHeight()
                    .pointerHoverIcon(PointerIcon.Hand),
                expanded = expanded
            )
        }

        ExposedDropdownMenu(
            modifier = Modifier
                .background(MarineBlueLighter),
            expanded = expanded,
            onDismissRequest = { setExpanded(false) }
        ) {
            movementDirections.forEach { md ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .pointerHoverIcon(PointerIcon.Hand),
                    onClick = {
                        setCurrentMovementDirection(md)
                        setExpanded(false)
                    },
                    text = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.shapes.gap),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .height(24.dp),
                                painter = painterResource(md.icon),
                                contentDescription = stringResource(md.label),
                                tint = TextColor
                            )
                            Text(
                                text = stringResource(md.label),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                )
            }
        }
    }
}
