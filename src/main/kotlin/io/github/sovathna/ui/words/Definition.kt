package io.github.sovathna.ui.words

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sovathna.AppText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Definition(
    wordId: Long,
    scope: CoroutineScope = rememberCoroutineScope { Dispatchers.Main.immediate },
    vm: DefinitionViewModel = remember { DefinitionViewModel() }
) {
    val state by vm.statesFlow.collectAsState(scope.coroutineContext)

    if (wordId > 0) {
        LaunchedEffect(wordId) {
            vm.getDefinition(wordId)
        }
    }

    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        content = {
            Box(Modifier.fillMaxSize().padding(16.dp)) {
                if (state.shouldShowDefinition) {
                    Column(Modifier.fillMaxSize()) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                modifier = Modifier.weight(1f).padding(start = 16.dp),
                                text = state.word,
                                fontSize = 28.sp,
                                letterSpacing = 0.sp
                            )
                            val res =
                                if (state.isBookmark) "bookmark_black_24dp.svg" else "bookmark_border_black_24dp.svg"
                            IconButton(
                                onClick = { scope.launch { vm.addOrDeleteBookmark(state.isBookmark) } },
                                content = {
                                    Icon(
                                        painter = painterResource(res),
                                        null,
                                        tint = MaterialTheme.colors.primary
                                    )
                                })
                        }
                        Spacer(Modifier.height(16.dp))
                        val str = buildAnnotatedString {
                            state.definition.replace("[HI]", "")
                                .replace("[HI1]", "")
                                .replace("[NewLine]", "\n\n")
                                .split("[]").forEach {
                                    if (it.contains("|")) {
                                        val arr = it.split("|")
                                        pushStringAnnotation(tag = "word", annotation = arr[0])
                                        withStyle(
                                            style = SpanStyle(color = MaterialTheme.colors.primary),
                                            block = { append(text = arr[1]) }
                                        )
                                        pop()
                                    } else {
                                        append(text = it)
                                    }
                                }
                        }
                        ClickableText(
                            modifier = Modifier.fillMaxWidth().verticalScroll(ScrollState(0)),
                            text = str,
                            style = TextStyle(
                                color = MaterialTheme.colors.onSurface,
                                fontSize = state.fontSize.sp,
                                fontStyle = MaterialTheme.typography.body1.fontStyle,
                                fontFamily = MaterialTheme.typography.body1.fontFamily,
                                letterSpacing = 0.sp
                            ),
                            onClick = { offset ->
                                str.getStringAnnotations(tag = "word", offset, offset).firstOrNull()?.let {
                                    scope.launch { vm.getDefinition(it.item.toLongOrNull() ?: 0L) }
                                }
                            }
                        )
                    }
                } else {
                    AppText(
                        text = "ចុចលើពាក្យដើម្បីមើលការពន្យល់",
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    )
}