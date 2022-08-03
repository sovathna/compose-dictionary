package io.github.sovathna.data.repository

import io.github.sovathna.data.dao.SelectDefinition
import io.github.sovathna.domain.Repository
import io.github.sovathna.domain.SettingsStore
import io.github.sovathna.domain.local.Database
import io.github.sovathna.model.ThemeType
import io.github.sovathna.model.WordUi
import io.github.sovathna.model.WordsType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RepositoryImpl : Repository, KoinComponent {
    private val database by inject<Database>()
    private val settings by inject<SettingsStore>()
    override suspend fun getWords(type: WordsType, filter: String, page: Int, pageSize: Int): List<WordUi> =
        withContext(Dispatchers.IO) {
            when (type) {
                WordsType.HISTORY -> {
                    database.historiesDaoQueries
                        .selectHistories(
                            filter = "$filter%",
                            limit = pageSize.toLong(),
                            offset = (pageSize * (page - 1)).toLong(),
                            mapper = { id, value -> WordUi(id, value) }
                        )
                }

                WordsType.BOOKMARK -> {
                    database.bookmarksDaoQueries
                        .selectBookmarks(
                            filter = "$filter%",
                            limit = pageSize.toLong(),
                            offset = (pageSize * (page - 1)).toLong(),
                            mapper = { id, value -> WordUi(id, value) }
                        )
                }

                else -> {
                    database.wordsDaoQueries
                        .selectWords(
                            filter = "$filter%",
                            limit = pageSize.toLong(),
                            offset = (pageSize * (page - 1)).toLong(),
                            mapper = { id, value -> WordUi(id, value) }
                        )
                }
            }.executeAsList()

        }

    override suspend fun getDefinition(wordId: Long): SelectDefinition =
        withContext(Dispatchers.IO) {
            database.wordsDaoQueries
                .selectDefinition(wordId)
                .executeAsOne()
        }

    override suspend fun addHistory(wordId: Long, word: String) = withContext(Dispatchers.IO) {
        with(database.historiesDaoQueries) {
            transaction {
                val old = selectHistory(wordId).executeAsOneOrNull()
                if (old != null) {
                    deleteHistory(old)
                }
                insertHistory(wordId, word)
            }
        }
    }

    override suspend fun isBookmark(wordId: Long) = withContext(Dispatchers.IO) {
        database.bookmarksDaoQueries.selectBookmark(wordId).executeAsOneOrNull() != null
    }

    override suspend fun addOrDeleteBookmark(isBookmark: Boolean, wordId: Long, word: String) =
        withContext(Dispatchers.IO) {
            with(database.bookmarksDaoQueries) {
                if (isBookmark) {
                    deleteBookmark(wordId)
                    false
                } else {
                    insertBookmark(wordId, word)
                    true
                }
            }
        }

    override suspend fun setThemeType(themeType: ThemeType) = settings.setThemeType(themeType)

    override suspend fun getThemeType() = settings.getThemeType()

    override suspend fun setFontSize(size: Float) = settings.setDefinitionFontSize(size)

    override suspend fun getFontSize() = settings.getDefinitionFontSize()

    override suspend fun getDataVersion() = settings.getDataVersion()

    override suspend fun setDataVersion(version: Int) = settings.setDataVersion(version)

    override suspend fun shouldDownloadData(newVersion: Int) = getDataVersion() < newVersion
}