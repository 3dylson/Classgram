package cv.edylsonf.classgram.domain.models

/**
 * Describes a venue associated with the class.
 */
data class Room(
    /**
     * Unique string identifying this room.
     */
    val id: String,

    /**
     * The name of the room.
     */
    val name: String
) {
    val abbreviatedName
        get() = name.split("|")[0]
}