package io.github.sovathna.domain

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface FileDownloadService {
    @Streaming
    @GET
    suspend fun downloadDatabase(@Url url: String): ResponseBody

}