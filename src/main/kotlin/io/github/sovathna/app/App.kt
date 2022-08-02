package io.github.sovathna.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sovathna.NavItemData
import io.github.sovathna.model.ThemeType
import io.github.sovathna.ui.about.AboutPage
import io.github.sovathna.ui.nav.NavView
import io.github.sovathna.ui.settings.SettingsPage
import io.github.sovathna.ui.splash.SplashPage
import io.github.sovathna.ui.theme.AppTheme
import io.github.sovathna.ui.words.WordsPage
import io.github.sovathna.model.WordsType
import kotlinx.coroutines.CoroutineScope

@Composable
fun App(
    scope: CoroutineScope = rememberCoroutineScope(),
    vm: AppViewModel = remember { AppViewModel(scope) }
) {
    val state by vm.statesFlow.collectAsState(scope.coroutineContext)

    AppTheme(
        useDarkTheme = when (state.themeType) {
            ThemeType.DARK -> true
            ThemeType.LIGHT -> false
            else -> isSystemInDarkTheme()
        },
        content = {
            Scaffold(content = {
                if (state.isSplash) {
                    SplashPage { vm.setSplash() }
                } else {
                    AppContent(
                        navItemsData = state.navItemsData,
                        selectedNavItemPosition = state.selectedNavItemPosition,
                        onNavItemSelected = vm::setSelectNavItem,
                        onThemeChanged = vm::setThemeType
                    )
                }
            })
        }
    )
}

@Composable
fun AppContent(
    navItemsData: List<NavItemData>,
    selectedNavItemPosition: Int,
    onNavItemSelected: (Int) -> Unit,
    onThemeChanged: (ThemeType) -> Unit
) {

    Row(Modifier.fillMaxSize().padding(16.dp)) {
        NavView(navItemsData = navItemsData, onItemSelected = onNavItemSelected)
        Spacer(Modifier.width(16.dp))
        Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
        Spacer(Modifier.width(16.dp))

        when (selectedNavItemPosition) {
            3 -> AboutPage()
            4 -> SettingsPage(onThemeChanged = onThemeChanged)
            else -> {
                val type = when (selectedNavItemPosition) {
                    1 -> WordsType.HISTORY
                    2 -> WordsType.BOOKMARK
                    else -> WordsType.HOME
                }
                WordsPage(type)
            }
        }
    }

}