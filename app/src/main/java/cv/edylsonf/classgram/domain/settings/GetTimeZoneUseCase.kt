package cv.edylsonf.classgram.domain.settings

import cv.edylsonf.classgram.data.prefs.PreferenceStorage
import cv.edylsonf.classgram.di.IoDispatcher
import cv.edylsonf.classgram.domain.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetTimeZoneUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Boolean>(dispatcher) {
    // TODO use as flow
    override suspend fun execute(parameters: Unit) =
        preferenceStorage.preferClassTimeZone.first()
}