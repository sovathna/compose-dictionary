package io.github.sovathna.data.remote

import io.github.sovathna.domain.FileDownloadService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.time.Duration

val fileDownloadServiceModule = module {
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
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://example.com/")
            .build()
    }

    factory {
        get<Retrofit>().create(FileDownloadService::class.java)
    }
}