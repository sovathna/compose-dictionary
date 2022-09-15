package io.github.sovathna.ui.words

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sovathna.model.WordsType

@Composable
fun WordsPage(type: WordsType) {
    val selectedWordState = remember { mutableStateOf<Long>(0) }
    WordList(
        type = type,
        onWordClick = { selectedWordState.value = it },
    )
    Spacer(Modifier.width(16.dp))
    Definition(wordId = selectedWordState.value)
}