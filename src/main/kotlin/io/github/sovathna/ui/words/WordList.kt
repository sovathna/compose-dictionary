package io.github.sovathna.ui.words

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sovathna.AppText
import io.github.sovathna.model.WordsType
import kotlinx.coroutines.CoroutineScope
import java.awt.Cursor

@Composable
fun WordList(
    type: WordsType,
    scope: CoroutineScope = rememberCoroutineScope(),
    vm: WordListViewModel = remember { WordListViewModel(scope) },
    onWordClick: (Long) -> Unit,
) {
    val state by vm.stateFlow.collectAsState(scope.coroutineContext)
    val lazyListState = rememberLazyListState()

    LaunchedEffect(type) {
        vm.getWords(type, "")
    }

    LaunchedEffect(state.words.firstOrNull()) {
        lazyListState.scrollToItem(0)
    }

    Box(Modifier.width(200.dp).fillMaxHeight()) {
        Column(Modifier.fillMaxSize()) {
            OutlinedTextField(
                state.filter,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = vm::setPreviewFilter,
                label = { Text("ស្វែងរកពាក្យ", letterSpacing = 0.sp) },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        Icons.Rounded.Search,
                        null,
                        Modifier.size(20.dp),
                        tint = MaterialTheme.colors.primary
                    )
                },
                trailingIcon = {
                    if (state.shouldShowClear) {
                        IconButton(
                            onClick = { vm.setPreviewFilter("") },
                            content = {
                                Icon(
                                    Icons.Rounded.Clear,
                                    null,
                                    Modifier.size(20.dp).pointerHoverIcon(PointerIcon(Cursor.getDefaultCursor()))
                                )
                            }
                        )
                    }
                }
            )
            Spacer(Modifier.height(16.dp))
            LazyColumn(Modifier.fillMaxSize(), state = lazyListState) {
                itemsIndexed(state.words, key = { _, word -> word.id }) { index, word ->
                    if (index == state.words.size - 4) {
                        LaunchedEffect(true) {
                            vm.shouldLoadMore()
                        }
                    }
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
                            .clickable {
                                onWordClick(word.id)
                            },
                        elevation = 3.dp,
                        shape = RoundedCornerShape(8.dp),
                        content = {
                            Text(
                                text = word.value,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp, horizontal = 16.dp),
                                letterSpacing = 0.sp
                            )
                        }
                    )

                }
            }
        }
        if (state.shouldShowEmpty) {
            AppText(Modifier.fillMaxWidth().align(Alignment.Center), "មិនមានពាក្យ", TextAlign.Center)
        }
    }
}