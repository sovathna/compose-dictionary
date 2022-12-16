package io.github.sovathna.ui.settings

import io.github.sovathna.domain.Repository
import io.github.sovathna.model.ThemeType
import io.github.sovathna.ui.BaseViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel : BaseViewModel<SettingsState>(SettingsState()),
    KoinComponent {

    private val repo by inject<Repository>()

    suspend fun init() {
        val themeType = repo.getThemeType()
        val fontSize = repo.getFontSize()
        setState(SettingsState(themeType, fontSize))
    }

    fun setPreviewFontSize(size: Float) {
        setState(current.copy(fontSize = size))
    }

    suspend fun setFontSize(size: Float) {

        repo.setFontSize(size)

    }

    suspend fun setThemeType(themeType: ThemeType) {
        repo.setThemeType(themeType)
        setState(current.copy(themeType = themeType))
    }
}

data class SettingsState(val themeType: ThemeType = ThemeType.SYSTEM, val fontSize: Float = 16f)