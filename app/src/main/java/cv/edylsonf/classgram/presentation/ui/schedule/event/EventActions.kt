package cv.edylsonf.classgram.presentation.ui.schedule.event

import cv.edylsonf.classgram.domain.models.EventId

/**
 * Actions that can be performed on events.
 */
public interface OnEventClickListener {
    fun openEventDetail(id: EventId)
}