package io.github.sovathna.ui.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sovathna.AppText
import io.github.sovathna.model.ThemeType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SettingsPage(
    scope: CoroutineScope = rememberCoroutineScope { Dispatchers.Main.immediate },
    viewModel: SettingsViewModel = remember { SettingsViewModel() },
    onThemeChanged: (ThemeType) -> Unit
) {
    val state by viewModel.statesFlow.collectAsState(scope.coroutineContext)

    LaunchedEffect(true) {
        viewModel.init()
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                content = {

                    fun setThemeType(themeType: ThemeType) {
                        scope.launch {
                            viewModel.setThemeType(themeType)
                            onThemeChanged(themeType)
                        }
                    }

                    AppText(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                        "ការកំណត់របស់កម្មវិធី",
                        fontSize = 22.sp
                    )
                    AppText(modifier = Modifier.fillMaxWidth().padding(top = 32.dp), text = "ពណ៌ផ្ទៃ")
                    CheckItem(
                        state.themeType == ThemeType.SYSTEM,
                        title = "ផ្ទៃប្រព័ន្ធ",
                        onCheckedChanged = {
                            setThemeType(ThemeType.SYSTEM)
                        })
                    CheckItem(
                        state.themeType == ThemeType.LIGHT,
                        title = "ផ្ទៃភ្លឺ",
                        onCheckedChanged = {
                            setThemeType(ThemeType.LIGHT)
                        })
                    CheckItem(
                        state.themeType == ThemeType.DARK,
                        title = "ផ្ទៃងងឹត",
                        onCheckedChanged = {
                            setThemeType(ThemeType.DARK)
                        })

                    AppText(
                        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                        "ទំហំអក្សរពន្យល់ន័យ",
                    )
                    Slider(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        value = state.fontSize,
                        onValueChange = { viewModel.setPreviewFontSize(it) },
                        valueRange = 16f..50f,
                        steps = 50 - 16,
                        onValueChangeFinished = { scope.launch { viewModel.setFontSize(state.fontSize) } }
                    )
                    AppText(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        "ភាសាខ្មែរ",
                        fontSize = state.fontSize.sp
                    )
                }
            )
        })
}

@Composable
private fun CheckItem(checked: Boolean = false, title: String, onCheckedChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(
            modifier = Modifier.align(Alignment.CenterVertically),
            checked = checked,
            onCheckedChange = { onCheckedChanged(it) }
        )
        AppText(
            modifier = Modifier.weight(1f).align(Alignment.CenterVertically).padding(start = 8.dp),
            text = title
        )
    }
}