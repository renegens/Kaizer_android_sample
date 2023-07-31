package eu.giant.kaizen.platform

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Int.formatToTime(): String {
    val sdf = SimpleDateFormat("hh:mm:ss", Locale.getDefault())
    val netDate = Date(this.toLong())
    return sdf.format(netDate)
}
