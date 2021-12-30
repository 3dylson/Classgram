package cv.edylsonf.classgram.presentation.ui.schedule.timetable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import cv.edylsonf.classgram.domain.models.Event
import cv.edylsonf.classgram.presentation.ui.theme.ClassgramTheme
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val EventTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a")

@Composable
fun BasicEvent(
    event: Event,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(end = 2.dp, bottom = 2.dp)
            .background(event.color ?: Color(0xFFAFBBF2), shape = RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {
        Text(
            text = "${event.start.format(EventTimeFormatter)} - ${event.end.format(EventTimeFormatter)}",
            style = MaterialTheme.typography.labelSmall,
        )

        Text(
            text = event.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
        )

        if (event.description != null) {
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

val sampleEvents = listOf(
    Event(
        id = "0",
        name = "Google I/O Keynote",
        color = Color(0xFFAFBBF2),
        start = ZonedDateTime.parse("2021-12-18T13:00:00+01:00[Europe/Lisbon]"),
        end = ZonedDateTime.parse("2021-12-18T15:00:00+01:00[Europe/Lisbon]"),
        description = "Tune in to find out about how we're furthering our mission to organize the world’s information and make it universally accessible and useful.",
        location = "Auditório"
        ),
    Event(
        id = "1",
        name = "Developer Keynote",
        color = null,
        start = ZonedDateTime.parse("2021-12-18T15:15:00+01:00[Europe/Lisbon]"),
        end = ZonedDateTime.parse("2021-12-18T16:00:00+01:00[Europe/Lisbon]"),
        description = "Learn about the latest updates to our developer products and platforms from Google Developers.",
        location = "Sala 123"
        ),
    Event(
        id = "2",
        name = "What's new in Android",
        color = Color(0xFF1B998B),
        start = ZonedDateTime.parse("2021-12-18T16:50:00+01:00[Europe/Lisbon]"),
        end = ZonedDateTime.parse("2021-12-18T17:00:00+01:00[Europe/Lisbon]"),
        description = "In this Keynote, Chet Haase, Dan Sandler, and Romain Guy discuss the latest Android features and enhancements for developers.",
    ),
    Event(
        id = "3",
        name = "What's new in Machine Learning",
        color = Color(0xFFF4BFDB),
        start = ZonedDateTime.parse("2021-12-19T09:30:00+01:00[Europe/Lisbon]"),
        end = ZonedDateTime.parse("2021-12-19T11:00:00+01:00[Europe/Lisbon]"),
        description = "Learn about the latest and greatest in ML from Google. We’ll cover what’s available to developers when it comes to creating, understanding, and deploying models for a variety of different applications.",
    ),
    Event(
        id = "4",
        name = "What's new in Material Design",
        color = Color(0xFF6DD3CE),
        start = ZonedDateTime.parse("2021-12-19T11:00:00+01:00[Europe/Lisbon]"),
        end = ZonedDateTime.parse("2021-12-19T12:15:00+01:00[Europe/Lisbon]"),
        description = "Learn about the latest design improvements to help you build personal dynamic experiences with Material Design.",
    ),
    Event(
        id = "5",
        name = "Jetpack Compose Basics",
        color = Color(0xFF1B998B),
        start = ZonedDateTime.parse("2021-05-20T12:00:00+01:00[Europe/Lisbon]"),
        end = ZonedDateTime.parse("2021-05-20T13:00:00+01:00[Europe/Lisbon]"),
        description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
    ),
)

class EventsProvider : PreviewParameterProvider<Event> {
    override val values = sampleEvents.asSequence()
}

/*We’re using @PreviewParameter and a PreviewParameterProvider to automatically
show multiple previews for each of our sample events instead of writing multiple
preview functions manually.*/
@Preview(showBackground = true)
@Composable
fun EventPreview(
    @PreviewParameter(EventsProvider::class) event: Event,
) {
    ClassgramTheme {
        BasicEvent(event, modifier = Modifier.sizeIn(maxHeight = 64.dp))
    }
}