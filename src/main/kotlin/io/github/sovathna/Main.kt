// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package io.github.sovathna

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.sovathna.app.App
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

fun main() {
    startKoin {
        printLogger(Level.DEBUG)
        modules(appModule)
    }

    application {
        Window(
            title = "Khmer Dictionary",
            icon = painterResource("ic_launcher_48.png"),
            onCloseRequest = ::exitApplication,
            content = { App() }
        )
    }
}

@Composable
fun AppText(
    modifier: Modifier,
    text: String,
    textAlign: TextAlign? = null,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        fontSize = fontSize,
        letterSpacing = 0.sp
    )
}