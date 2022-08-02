package io.github.sovathna.data.settings

import com.google.gson.annotations.SerializedName
import io.github.sovathna.model.ThemeType

data class AppSettings(
    @SerializedName("theme_type")
    val themeType: ThemeType = ThemeType.SYSTEM,
    @SerializedName("definition_font_size")
    val definitionFontSize: Float = 16f,
    @SerializedName("data_version")
    val dataVersion: Int = 0
)
