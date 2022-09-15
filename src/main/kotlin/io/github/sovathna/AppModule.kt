package io.github.sovathna

import com.google.gson.GsonBuilder
import io.github.sovathna.data.repository.RepositoryImpl
import io.github.sovathna.data.settings.SettingsStoreImpl
import io.github.sovathna.domain.Repository
import io.github.sovathna.domain.SettingsStore
import org.koin.dsl.module


val appModule = module {
    single<Repository> {
        RepositoryImpl()
    }

    single {
        GsonBuilder()
            .serializeNulls()
            .setLenient()
            .setPrettyPrinting()
            .create()
    }

    single<SettingsStore> {
        SettingsStoreImpl()
    }
}