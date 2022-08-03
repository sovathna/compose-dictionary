package io.github.sovathna.domain

import io.github.sovathna.model.ThemeType

interface SettingsStore {
    suspend fun getDataVersion(): Int

    suspend fun setDataVersion(version: Int)

    suspend fun setThemeType(themeType: ThemeType)

    suspend fun getThemeType(): ThemeType

    suspend fun setDefinitionFontSize(size: Float)

    suspend fun getDefinitionFontSize(): Float
}