package io.github.sovathna.ui.words

import io.github.sovathna.model.WordUi

data class WordsState(
    val isInit: Boolean = true,
    val isLoading: Boolean = true,
    val isMore: Boolean = true,
    val page: Int = 1,
    val error: String? = null,
    val words: List<WordUi> = emptyList(),
    val word: WordUi? = null,
    val definition: String? = null,
    val isBookmark: Boolean? = null
) {
    val shouldShowEmpty = words.isEmpty() && !isLoading
}