package io.github.sovathna.ui.nav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sovathna.AppText
import io.github.sovathna.NavItemData


@Composable
fun NavView(
    navItemsData: List<NavItemData>,
    onItemSelected: (Int) -> Unit
) {
    Card(
        modifier = Modifier.width(220.dp).fillMaxHeight(),
        elevation = 3.dp,
        shape = RoundedCornerShape(10.dp),
        content = {
            Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                navItemsData.forEachIndexed { index, data ->
                    NavViewItem(id = index, navItemData = data, onClick = onItemSelected)
                    if (index == 2) Spacer(Modifier.weight(1F))
                }
                Divider()
                AppText(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
                    text = "វចនានុក្រមខ្មែរ ជំនាន់១.០",
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }
    )
}

@Composable
fun NavViewItem(
    id: Int,
    navItemData: NavItemData,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick(id) },
        backgroundColor = if (navItemData.isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background,
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp,
        content = {
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Icon(
                        painterResource(navItemData.icon),
                        contentDescription = navItemData.title,
                        Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    AppText(
                        modifier = Modifier.weight(1F),
                        text = navItemData.title,
                    )
                }
            )
        })
}