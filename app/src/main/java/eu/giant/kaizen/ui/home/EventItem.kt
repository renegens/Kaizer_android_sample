package eu.giant.kaizen.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eu.giant.kaizen.domain.model.SportEvent
import eu.giant.kaizen.platform.formatToTime
import eu.giant.kaizen.ui.components.CountdownTimer
import eu.giant.kaizen.ui.theme.White
import eu.giant.kaizen.ui.theme.Yellow
import eu.giant.kaizer.R

@Composable
fun EventItem(
    event: SportEvent, onFavoriteClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onFavoriteClicked.invoke(event.id) },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CountdownTimer(targetTime = event.startTime, content = {
            Text(
                text = it.toInt().formatToTime(),
                fontSize = 12.sp,
                color = MaterialTheme.colors.White
            )
        })
        Spacer(modifier = Modifier.height(4.dp))
        Icon(
            modifier = Modifier.size(16.dp),
            painter = painterResource(id = if (event.isFavorite) R.drawable.favorite_filled else R.drawable.favorite_outlined),
            contentDescription = "Favorite",
            tint = if (event.isFavorite) MaterialTheme.colors.Yellow else MaterialTheme.colors.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        EventDetails(name = event.name)
    }
}

@Composable
private fun EventDetails(name: String) {
    Text(
        text = name.split("-").first().trim(),
        fontSize = 12.sp,
        color = MaterialTheme.colors.White,
        textAlign = TextAlign.Center
    )
    Text(text = "vs", color = Color.Red)
    Text(
        text = name.split("-").last().trim(),
        fontSize = 12.sp,
        color = MaterialTheme.colors.White,
        textAlign = TextAlign.Center
    )
}
