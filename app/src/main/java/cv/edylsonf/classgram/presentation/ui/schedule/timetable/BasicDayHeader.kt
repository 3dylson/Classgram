package cv.edylsonf.classgram.presentation.ui.schedule

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun BasicDayHeader(
    day: LocalDate,
    modifier: Modifier = Modifier,
) {
    Text(
        text = day.format(DayFormatter),
        textAlign = TextAlign.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
    )
}

private val DayFormatter = DateTimeFormatter.ofPattern("EE, MMM d")

@Preview(showBackground = true)
@Composable
fun BasicDayHeaderPreview() {
    ClassgramTheme {
        BasicDayHeader(day = LocalDate.now())
    }
}