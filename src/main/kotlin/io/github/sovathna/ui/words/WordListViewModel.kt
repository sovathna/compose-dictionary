package io.github.sovathna.ui.words

import io.github.sovathna.Const
import io.github.sovathna.domain.Repository
import io.github.sovathna.model.WordUi
import io.github.sovathna.model.WordsType
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(FlowPreview::class)
class WordListViewModel : KoinComponent {

    private val state = MutableStateFlow(WordListState())
    val stateFlow: StateFlow<WordListState> = state
    private val current get() = state.value

    private fun setState(state: WordListState) {
        this.state.value = state
    }

    private val repo by inject<Repository>()

    private val filterFlow = MutableSharedFlow<String>(extraBufferCapacity = 1)

    suspend fun init() {
        filterFlow.distinctUntilChanged().debounce(450L).collectLatest {
            getWords(type = current.type, page = 1, filter = it)
        }
    }

    suspend fun setPreviewFilter(filter: String) {
        if (current.filter == filter) return
        filterFlow.emit(filter)
        setState(current.copy(filter = filter))
    }

    suspend fun getWords(type: WordsType, filter: String, page: Int = current.page, isLoadingMore: Boolean = false) {
        val newPage = if (isLoadingMore) page + 1 else page
        setState(
            current.copy(
                isLoading = true,
                page = newPage,
                isMore = false,
                type = type,
                filter = filter
            )
        )
        val tmp = repo.getWords(type, filter, current.page, Const.PAGE_SIZE)
        val words = if (current.page == 1) tmp else current.words.toMutableList().apply { addAll(tmp) }
        setState(
            current.copy(
                isLoading = false,
                words = words,
                isMore = tmp.size >= Const.PAGE_SIZE
            )
        )
    }

    suspend fun shouldLoadMore() {
        if (!current.shouldLoadMore) return
        println("load more")
        getWords(type = current.type, filter = current.filter, isLoadingMore = true)
    }
}

data class WordListState(
    val isLoading: Boolean = true,
    val isMore: Boolean = false,
    val type: WordsType = WordsType.HOME,
    val page: Int = 1,
    val words: List<WordUi> = emptyList(),
    val filter: String = ""
) {
    val shouldShowEmpty = !isLoading && words.isEmpty()
    val shouldShowClear = filter.isNotBlank()
    val shouldLoadMore = !isLoading && isMore
}