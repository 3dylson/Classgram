package cv.edylsonf.classgram.domain.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZonedDateTime

typealias SessionId = String

/**
 * Describes a class session. Sessions have specific start and end times, and they represent a
 * variety of class events: talks, sandbox demos, office hours, etc. A session is usually
 * associated with one or more [ClassTag]s.
 */
data class Session(
    /**
     * Unique string identifying this session.
     */
    val id: SessionId,

    /**
     * Start time of the session
     */
    val startTime: ZonedDateTime,

    /**
     * End time of the session
     */
    val endTime: ZonedDateTime,

    /**
     * Session title.
     */
    val title: String,

    /**
     * Body of text explaining this session in detail.
     */
    val description: String,

    /**
     * The session room.
     */
    val room: Room?,

    /**
     * Full URL for the session online.
     */
    val sessionUrl: String,

    /**
     * Indicates if the Session has a live stream.
     */
    val isLivestream: Boolean,

    /**
     * The [ClassTag]s associated with the session. Ordered, with the most important tags appearing
     * first.
     */
    val tags: List<ClassTag>,

    /**
     * Subset of [ClassTag]s that are for visual consumption.
     */
    val displayTags: List<ClassTag>,

    /**
     * IDs of the sessions related to this session.
     */
    val relatedSessions: Set<SessionId>
) {

    /**
     * Returns whether the session is currently being live streamed or not.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun isLive(): Boolean {
        val now = ZonedDateTime.now()
        // TODO: Determine when a session is live based on the time AND the liveStream being
        // available.
        return startTime <= now && endTime >= now
    }

    /**
     * The type of the event e.g. Subject, Codelab etc.
     */
    val type: SessionType by lazy(LazyThreadSafetyMode.PUBLICATION) {
        SessionType.fromTags(tags)
    }

    fun levelTag(): ClassTag? {
        return tags.firstOrNull { it.category == ClassTag.CATEGORY_LEVEL }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun isOverlapping(session: Session): Boolean {
        return this.startTime < session.endTime && this.endTime > session.startTime
    }

    /**
     * Detailed description of this event. Includes description, speakers, and live-streaming URL.
     */
    fun getCalendarDescription(
        paragraphDelimiter: String,
        speakerDelimiter: String
    ): String = buildString {
        append(description)
        append(paragraphDelimiter)
        //append(speakers.joinToString(speakerDelimiter) { speaker -> speaker.name })
    }
}

/**
 * Represents the type of the event e.g. Session, Codelab etc.
 */
enum class SessionType(val displayName: String) {

    SESSION("Session"),
    OFFICE_HOURS("Office Hours"),
    MEETUP("Meetup"),
    UNKNOWN("Unknown");

    companion object {

        /**
         * Examine the given [tags] to determine the [SessionType]. Defaults to [SESSION] if no
         * category tag is found.
         */
        fun fromTags(tags: List<ClassTag>): SessionType {
            val typeTag = tags.firstOrNull { it.category == ClassTag.CATEGORY_TYPE }

            return when (typeTag?.tagName) {
                ClassTag.TYPE_SESSIONS -> SESSION
                ClassTag.TYPE_OFFICEHOURS -> OFFICE_HOURS
                ClassTag.TYPE_MEETUPS -> MEETUP
                else -> UNKNOWN
            }
        }

    }
}