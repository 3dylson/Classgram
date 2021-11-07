package cv.edylsonf.classgram.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime

data class ClassDay(
    val start: ZonedDateTime,
    val end: ZonedDateTime
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun contains(session: Session) = start <= session.startTime && end >= session.endTime
}