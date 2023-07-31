package eu.giant.kaizen.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import eu.giant.kaizen.domain.model.Sport
import eu.giant.kaizen.ui.theme.Black
import eu.giant.kaizen.ui.theme.Blue
import eu.giant.kaizen.ui.theme.Grey
import eu.giant.kaizen.ui.theme.Red
import eu.giant.kaizen.ui.theme.White
import java.util.Locale

@Composable
fun SportHeader(
    sport: Sport,
    isFiltered: Boolean,
    isExpanded: Boolean,
    onFilterClicked: (String) -> Unit,
    onToggleEventVisibilityClicked: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .background(MaterialTheme.colors.White),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.Red)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = sport.name.uppercase(Locale.getDefault()), color = MaterialTheme.colors.Black)
        Spacer(Modifier.weight(1f))
        Switch(
            checked = isFiltered,
            onCheckedChange = { onFilterClicked.invoke(sport.id) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colors.Blue,
                checkedTrackColor = MaterialTheme.colors.Blue,
                uncheckedThumbColor = MaterialTheme.colors.Grey,
                uncheckedTrackColor = MaterialTheme.colors.Grey,
            ),
        )
        IconButton(onClick = { onToggleEventVisibilityClicked.invoke(sport.id) }) {
            Icon(
                imageVector = if (isExpanded) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                contentDescription = "",
                tint = MaterialTheme.colors.Black
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}
