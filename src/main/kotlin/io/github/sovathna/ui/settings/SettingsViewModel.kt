package io.github.sovathna.ui.settings

import io.github.sovathna.domain.Repository
import io.github.sovathna.model.ThemeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SettingsViewModel(private val scope: CoroutineScope) : KoinComponent {
    private val states = MutableStateFlow(SettingsState())
    val statesFlow: StateFlow<SettingsState> = states
    private val current get() = states.value

    private fun setState(state: SettingsState) {
        states.value = state
    }

    private val repo by inject<Repository>()

    init {
        scope.launch {
            val themeType = repo.getThemeType()
            val fontSize = repo.getFontSize()
            setState(SettingsState(themeType, fontSize))
        }
    }

    fun setPreviewFontSize(size: Float) {
        setState(current.copy(fontSize = size))
    }

    fun setFontSize(size: Float) {
        scope.launch {
            repo.setFontSize(size)
        }
    }

    fun setThemeType(themeType: ThemeType) {
        scope.launch {
            repo.setThemeType(themeType)
            setState(current.copy(themeType = themeType))
        }
    }
}

data class SettingsState(val themeType: ThemeType = ThemeType.SYSTEM, val fontSize: Float = 16f)