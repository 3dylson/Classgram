package cv.edylsonf.classgram.presentation.ui.schedule.timetable

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private val HourFormatter = DateTimeFormatter.ofPattern("h a")

@Composable
fun BasicSidebarLabel(
    time: LocalTime,
    modifier: Modifier = Modifier,
) {
    Text(
        text = time.format(HourFormatter),
        modifier = modifier
            .fillMaxHeight()
            .padding(4.dp),
        color = MaterialTheme.colorScheme.onBackground

    )
}

@Preview(showBackground = true)
@Composable
fun BasicSidebarLabelPreview() {
    ClassgramTheme {
        BasicSidebarLabel(time = LocalTime.NOON, Modifier.sizeIn(maxHeight = 64.dp))
    }
}