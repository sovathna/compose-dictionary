package io.github.sovathna.app

import io.github.sovathna.data.AppRepository
import io.github.sovathna.model.ThemeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppViewModel(private val scope: CoroutineScope) : KoinComponent {

    private val states = MutableStateFlow(AppState())
    val statesFlow: StateFlow<AppState> = states
    private val current get() = states.value

    private fun setState(state: AppState) {
        states.value = state
    }

    private val repo by inject<AppRepository>()

    init {
        getThemeType()
    }

    fun setSplash() {
        setState(current.copy(isSplash = false))
    }

    private fun getThemeType() {
        scope.launch {
            val themeType = repo.getThemeType()
            setThemeType(themeType)
        }
    }

    fun setThemeType(themeType: ThemeType) {
        scope.launch {
            val newState = current.copy(themeType = themeType)
            setState(newState)
        }

    }

    fun setSelectNavItem(position: Int) {
        if (position == current.selectedNavItemPosition) return
        scope.launch {
            val newData = current.navItemsData.toMutableList().apply {
                this[current.selectedNavItemPosition] = this[current.selectedNavItemPosition].copy(isSelected = false)
                this[position] = this[position].copy(isSelected = true)
            }
            setState(current.copy(navItemsData = newData, selectedNavItemPosition = position))
        }
    }

}