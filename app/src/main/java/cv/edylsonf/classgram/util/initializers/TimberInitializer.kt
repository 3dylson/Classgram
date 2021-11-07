package cv.edylsonf.classgram.util.initializers

import android.content.Context
import androidx.startup.Initializer
import cv.edylsonf.classgram.BuildConfig
import cv.edylsonf.classgram.util.CrashlyticsTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}