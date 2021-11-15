package cv.edylsonf.classgram.data.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import cv.edylsonf.classgram.data.prefs.DataStorePreferenceStorage.PreferencesKeys.PREF_CLASS_TIME_ZONE
import cv.edylsonf.classgram.data.prefs.DataStorePreferenceStorage.PreferencesKeys.PREF_SNACKBAR_IS_STOPPED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Storage for app and user preferences.
 */
interface PreferenceStorage {

    suspend fun stopSnackbar(stop: Boolean)
    // TODO make this a flow or a suspend function
    suspend fun isSnackbarStopped(): Boolean

    suspend fun preferClassTimeZone(preferClassTimeZone: Boolean)
    val preferClassTimeZone: Flow<Boolean>
}

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceStorage {
    companion object {
        const val PREFS_NAME = "classgram"
    }

    object PreferencesKeys{
        val PREF_SNACKBAR_IS_STOPPED = booleanPreferencesKey("pref_snackbar_is_stopped")
        val PREF_CLASS_TIME_ZONE = booleanPreferencesKey("pref_class_time_zone")
    }

    override suspend fun stopSnackbar(stop: Boolean) {
        dataStore.edit {
            it[PREF_SNACKBAR_IS_STOPPED] = stop
        }
    }

    override suspend fun isSnackbarStopped(): Boolean {
        return dataStore.data.map { it[PREF_SNACKBAR_IS_STOPPED] ?: false }.first()
    }

    override suspend fun preferClassTimeZone(preferClassTimeZone: Boolean) {
        dataStore.edit {
            it[PREF_CLASS_TIME_ZONE] = preferClassTimeZone
        }
    }

    override val preferClassTimeZone =
        dataStore.data.map { it[PREF_CLASS_TIME_ZONE] ?: true }


}