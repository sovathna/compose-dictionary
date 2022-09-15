package io.github.sovathna

import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val appDataFileModule = module {
    single(qualifier = named("local_data_dir")) {
        val dataDir =
            if (System.getProperty("os.name").contains("win", true)) {
                File(System.getenv("LOCALAPPDATA"), "Khmer Dictionary")
            } else {
                try {
                    File(System.getProperty("compose.application.resources.dir"))
                } catch (e: Exception) {
                    File(System.getProperty("user.dir"))
                }
            }
        if (!dataDir.exists()) dataDir.mkdirs()
        dataDir
    }

    single(qualifier = named("database_file")) {
        File(get<File>(qualifier = named("local_data_dir")), "data.sqlite")
    }

    single(qualifier = named("local_database_file")) {
        File(get<File>(qualifier = named("local_data_dir")), "local_data.sqlite")
    }

    single(qualifier = named("settings_file")) {
        File(get<File>(qualifier = named("local_data_dir")), "settings.config").also {
            if (!it.exists()) it.createNewFile()
        }
    }
}