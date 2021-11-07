package cv.edylsonf.classgram.presentation.ui.schedule

import cv.edylsonf.classgram.domain.models.ClassDay

/** An indicator for days on the Schedule. */
class DayIndicator(
    val day: ClassDay,
    val checked: Boolean = false,
    val enabled: Boolean = true
) {
    // Only the day is used for equality
    override fun equals(other: Any?): Boolean =
        this === other || (other is DayIndicator && day == other.day)

    // Only the day is used for equality
    override fun hashCode(): Int = day.hashCode()

    fun areUiContentsTheSame(other: DayIndicator): Boolean {
        return checked == other.checked && enabled == other.enabled
    }
}