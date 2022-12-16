package io.github.sovathna.data.settings

import com.google.gson.Gson
import io.github.sovathna.domain.SettingsStore
import io.github.sovathna.model.AppSettings
import io.github.sovathna.model.ThemeType
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import kotlin.coroutines.CoroutineContext

class SettingsStoreImpl : SettingsStore, KoinComponent {
    private val gson by inject<Gson>()
    private val file by inject<File>(qualifier = named("settings_file"))
    private val ioDispatcher by inject<CoroutineContext>(qualifier = named("io_dispatcher"))

    override suspend fun getDataVersion() =
        withContext(ioDispatcher) { getSettings().dataVersion }

    override suspend fun setDataVersion(version: Int) =
        withContext(ioDispatcher) {
            val settings = getSettings().copy(dataVersion = version)
            setSettings(settings)
        }

    override suspend fun setThemeType(themeType: ThemeType) =
        withContext(ioDispatcher) {
            val settings = getSettings().copy(themeType = themeType)
            setSettings(settings)
        }

    override suspend fun getThemeType(): ThemeType =
        withContext(ioDispatcher) { getSettings().themeType }

    override suspend fun setDefinitionFontSize(size: Float) {
        val settings = getSettings().copy(definitionFontSize = size)
        setSettings(settings)
    }

    override suspend fun getDefinitionFontSize(): Float =
        withContext(ioDispatcher) {
            getSettings().definitionFontSize
        }

    private suspend fun getSettings(): AppSettings =
        withContext(ioDispatcher) {
            var inputStream: InputStream? = null
            try {
                inputStream = FileInputStream(file)
                val json = String(inputStream.readBytes())
                inputStream.close()
                gson.fromJson(json, AppSettings::class.java) ?: AppSettings()
            } catch (e: Exception) {
                e.printStackTrace()
                AppSettings()
            } finally {
                inputStream?.close()
            }
        }

    private suspend fun setSettings(settings: AppSettings) =
        withContext(ioDispatcher) {
            val outputStream = FileOutputStream(file)
            val bytes = gson.toJson(settings).toByteArray()
            outputStream.write(bytes)
            outputStream.close()
        }
}