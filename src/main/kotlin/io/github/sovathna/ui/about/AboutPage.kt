package io.github.sovathna.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sovathna.AppText
import java.awt.Desktop
import java.net.URI

@Composable
fun AboutPage() {
    Card(
        modifier = Modifier.fillMaxSize(),
        elevation = 3.dp,
        shape = RoundedCornerShape(8.dp),
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                content = {
                    AppText(
                        modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                        "អំពីកម្មវិធី",
                        fontSize = 22.sp
                    )
                    AppText(
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                        "វចនានុក្រមខ្មែរ",
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    )
                    AppText(
                        modifier = Modifier.fillMaxWidth().padding(top = 32.dp),
                        text = "ពុម្ពអក្សរប្រើប្រាស់\nSuwannaphum\nDesigned by Danh Hong",
                        textAlign = TextAlign.Center
                    )
                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 32.dp),
                        onClick = { Desktop.getDesktop().browse(URI("https://sovathna.github.io/")) },
                        content = { Text(text = "អភិវឌ្ឍកម្មវិធីដោយ ហុង សុវឌ្ឍនា", letterSpacing = 0.sp) }
                    )
                    TextButton(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            Desktop.getDesktop().browse(URI("https://github.com/sovathna/compose-dictionary"))
                        },
                        content = { Text(text = "កូដរបស់កម្មវិធីនេះមាននៅលើGitHub", letterSpacing = 0.sp) }
                    )
                }
            )
        })
}