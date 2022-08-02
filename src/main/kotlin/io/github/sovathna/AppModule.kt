package io.github.sovathna

import com.google.gson.GsonBuilder
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.github.sovathna.data.AppRepository
import io.github.sovathna.data.FileDownloadService
import io.github.sovathna.data.settings.AppSettingsStore
import io.github.sovathna.domain.local.Database
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.io.File
import java.time.Duration


val appModule = module {

    single {
        val file = get<File>(qualifier = named("database_file"))
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePath}")
        Database.Schema.migrate(driver, 1, 2)
        Database(driver)
    }
    single {
        AppRepository()
    }
    single {
        GsonBuilder()
            .serializeNulls()
            .setLenient()
            .setPrettyPrinting()
            .create()
    }
    single {
        AppSettingsStore()
    }

    single(qualifier = named("local_data_dir")) {
        if (System.getProperty("os.name").contains("win", true)) {
            File(System.getenv("LOCALAPPDATA"), "Khmer Dictionary")
        } else {
            try {
                File(System.getProperty("compose.application.resources.dir"))
            } catch (e: Exception) {
                File(System.getProperty("user.dir"))
            }
        }
    }

    single(qualifier = named("database_file")) {
        File(get<File>(qualifier = named("local_data_dir")), "data.sqlite")
    }

    factory {
        OkHttpClient.Builder()
            .readTimeout(Duration.ZERO)
            .addInterceptor(
                HttpLoggingInterceptor { println(it) }
                    .apply { level = HttpLoggingInterceptor.Level.HEADERS }
            )
            .build()
    }

    factory {
        val retrofit = Retrofit.Builder()
            .client(get())
            .baseUrl("https://example.com/")
            .build()
        retrofit.create(FileDownloadService::class.java)
    }
}