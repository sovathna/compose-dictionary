package io.github.sovathna.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sovathna.AppText
import kotlinx.coroutines.CoroutineScope
import kotlin.math.ceil

@Composable
fun SplashPage(
    scope: CoroutineScope = rememberCoroutineScope(),
    vm: SplashViewModel = remember { SplashViewModel(scope) },
    onDone: () -> Unit
) {
    val state by vm.statesFlow.collectAsState(scope.coroutineContext)

    LaunchedEffect(true) {
        vm.downloadAndUnzip()
    }

    if (state.shouldRedirect) {
        LaunchedEffect(true) {
            onDone()
        }
    }

    fun numToKhNum(str: String): String {
        var newStr = ""
        str.forEach {
            newStr +=
                when (it) {
                    '1' -> "១"
                    '2' -> "២"
                    '3' -> "៣"
                    '4' -> "៤"
                    '5' -> "៥"
                    '6' -> "៦"
                    '7' -> "៧"
                    '8' -> "៨"
                    '9' -> "៩"
                    '0' -> "០"
                    else -> "$it"
                }
        }
        return newStr
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        content = {
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight(.5f),
                content = {
                    Image(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxHeight(.5f)
                            .aspectRatio(1f),
                        painter = painterResource("splash.png"),
                        contentDescription = null
                    )
                })
            AppText(
                modifier = Modifier.fillMaxWidth(.5f).align(Alignment.CenterHorizontally),
                text = when {
                    state.progress <= 0f -> "រៀបចំទាញយកទិន្នន័យលើកដំបូង... សូមមេត្តារង់ចាំ!"
                    state.progress >= 1f -> "វចនានុក្រមខ្មែរ"
                    else -> "កំពុងទាញយកទិន្នន័យលើកដំបូង... សូមមេត្តារង់ចាំ!"
                },
                fontSize = if (state.progress >= 1f) 22.sp else 18.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(32.dp))
            val progressModifier = Modifier
                .fillMaxWidth(.5f)
                .align(Alignment.CenterHorizontally)
            if (0f < state.progress && state.progress < 1f) {
                LinearProgressIndicator(modifier = progressModifier, progress = state.progress)
                Spacer(Modifier.height(8.dp))
                AppText(
                    modifier = Modifier.fillMaxWidth(.5f).align(Alignment.CenterHorizontally),
                    text = numToKhNum(ceil(state.progress * 100).toInt().toString()) + "%",
                    fontSize = 14.sp,
                    textAlign = TextAlign.End
                )
            } else {
                LinearProgressIndicator(modifier = progressModifier)
            }
        })

}