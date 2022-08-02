package io.github.sovathna.data.settings

import com.google.gson.Gson
import io.github.sovathna.model.ThemeType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream

class AppSettingsStore : KoinComponent {
    private val gson by inject<Gson>()
    private val dataDir by inject<File>(qualifier = named("local_data_dir"))
    private val file = File(dataDir, "settings.config")

    private suspend fun ensureCreateDir() = withContext(Dispatchers.IO) {
        if (!dataDir.exists()) {
            dataDir.mkdirs()
        }
    }

    suspend fun getDataVersion() = getSettings().dataVersion

    suspend fun setDataVersion(version: Int) {
        val settings = getSettings().copy(dataVersion = version)
        setSettings(settings)
    }

    suspend fun setThemeType(themeType: ThemeType) {
        val settings = getSettings().copy(themeType = themeType)
        setSettings(settings)
    }

    suspend fun getThemeType(): ThemeType = getSettings().themeType

    suspend fun setDefinitionFontSize(size: Float) {
        val settings = getSettings().copy(definitionFontSize = size)
        setSettings(settings)
    }

    suspend fun getDefinitionFontSize(): Float = getSettings().definitionFontSize

    private suspend fun getSettings(): AppSettings = withContext(Dispatchers.IO) {
        var inputStream: InputStream? = null
        try {
            ensureCreateDir()
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

    private suspend fun setSettings(settings: AppSettings) = withContext(Dispatchers.IO) {
        val outputStream = FileOutputStream(file)
        val bytes = gson.toJson(settings).toByteArray()
        outputStream.write(bytes)
        outputStream.close()
    }
}