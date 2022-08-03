package io.github.sovathna

import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val appDataFileModule = module {
    single(qualifier = named("local_data_dir")) {
        if (System.getProperty("os.name").contains("win", true)) {
            File(System.getenv("LOCALAPPDATA"), "Khmer Dictionary").apply {
                if (!exists()) mkdirs()
            }
        } else {
            try {
                File(System.getProperty("compose.application.resources.dir"))
            } catch (e: Exception) {
                File(System.getProperty("user.dir"))
            }
        }
    }

    single(qualifier = named("database_file")) {
        File(get<File>(qualifier = named("local_data_dir")), "data.sqlite").also {
            if (!it.exists()) it.createNewFile()
        }
    }

    single(qualifier = named("settings_file")) {
        File(get<File>(qualifier = named("local_data_dir")), "settings.config").also {
            if (!it.exists()) it.createNewFile()
        }
    }
}