package io.github.sovathna.model

import com.google.gson.annotations.SerializedName

data class AppSettings(
    @SerializedName("theme_type")
    val themeType: ThemeType = ThemeType.SYSTEM,
    @SerializedName("definition_font_size")
    val definitionFontSize: Float = 16f,
    @SerializedName("data_version")
    val dataVersion: Int = 0
)
