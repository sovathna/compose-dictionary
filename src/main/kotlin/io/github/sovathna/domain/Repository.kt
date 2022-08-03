package io.github.sovathna.domain

import io.github.sovathna.data.dao.SelectDefinition
import io.github.sovathna.model.ThemeType
import io.github.sovathna.model.WordUi
import io.github.sovathna.model.WordsType

interface Repository {
    suspend fun getWords(type: WordsType, filter: String, page: Int, pageSize: Int): List<WordUi>

    suspend fun getDefinition(wordId: Long): SelectDefinition

    suspend fun addHistory(wordId: Long, word: String)

    suspend fun isBookmark(wordId: Long): Boolean

    suspend fun addOrDeleteBookmark(isBookmark: Boolean, wordId: Long, word: String): Boolean

    suspend fun setThemeType(themeType: ThemeType)

    suspend fun getThemeType(): ThemeType

    suspend fun setFontSize(size: Float)

    suspend fun getFontSize(): Float

    suspend fun getDataVersion(): Int

    suspend fun setDataVersion(version: Int)

    suspend fun shouldDownloadData(newVersion: Int): Boolean
}