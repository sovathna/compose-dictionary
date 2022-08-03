package io.github.sovathna.ui.words

import io.github.sovathna.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefinitionViewModel(private val scope: CoroutineScope) : KoinComponent {
    private val states = MutableStateFlow(DefinitionState())
    val statesFlow: StateFlow<DefinitionState> = states
    private val current get() = states.value

    private fun setState(state: DefinitionState) {
        states.value = state
    }

    private val repo by inject<Repository>()

    fun getDefinition(wordId: Long) {
        if (wordId <= 0) return
        scope.launch {
            val def = repo.getDefinition(wordId)
            val isBookmark = repo.isBookmark(wordId)
            val fontSize = repo.getFontSize()
            repo.addHistory(wordId, def.word)
            setState(
                current.copy(
                    wordId = wordId,
                    word = def.word,
                    definition = def.definition,
                    fontSize = fontSize,
                    isBookmark = isBookmark
                )
            )
        }
    }

    fun addOrDeleteBookmark(isBookmark: Boolean) {
        scope.launch {
            val newBookmark = repo.addOrDeleteBookmark(isBookmark, current.wordId, current.word)
            setState(current.copy(isBookmark = newBookmark))
        }
    }
}

data class DefinitionState(
    val wordId: Long = 0,
    val word: String = "",
    val definition: String = "",
    val fontSize: Float = 16f,
    val isBookmark: Boolean = false
) {
    val shouldShowDefinition = definition.isNotBlank()
}