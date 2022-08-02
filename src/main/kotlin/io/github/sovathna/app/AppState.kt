package io.github.sovathna.app

import io.github.sovathna.NavItemData
import io.github.sovathna.model.ThemeType


private val defaultNavItems = listOf(
    NavItemData("បញ្ជីពាក្យ", "menu_book_black_24dp.svg", true),
    NavItemData("បញ្ជីពាក្យធ្លាប់មើល", "history_black_24dp.svg", false),
    NavItemData("បញ្ជីពាក្យចំណាំ", "bookmarks_black_24dp.svg", false),
    NavItemData("អំពីកម្មវិធី", "info_black_24dp.svg", false),
    NavItemData("ការកំណត់", "settings_black_24dp.svg", false),
)

data class AppState(
    val isSplash: Boolean = true,
    val themeType: ThemeType = ThemeType.SYSTEM,
    val navItemsData: List<NavItemData> = defaultNavItems,
    val selectedNavItemPosition: Int = 0,
)