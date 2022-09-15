package io.github.sovathna.ui.splash

import io.github.sovathna.Const
import io.github.sovathna.domain.FileDownloadService
import io.github.sovathna.domain.Repository
import io.github.sovathna.ui.BaseViewModel
import kotlinx.coroutines.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.zip.ZipInputStream

class SplashViewModel(private val scope: CoroutineScope) : BaseViewModel<SplashState>(SplashState()), KoinComponent {


    private val file by inject<File>(qualifier = named("database_file"))
    private val repo by inject<Repository>()
    private val downloadService by inject<FileDownloadService>()

    private fun setProgress(progress: Float) {
        scope.launch {
            setState(current.copy(progress = progress))
        }
    }

    suspend fun downloadAndUnzip() = withContext(Dispatchers.IO) {
        var dbOutputStream: FileOutputStream? = null
        var downloadInputStream: InputStream? = null
        var zipInputStream: ZipInputStream? = null
        try {
            if (repo.shouldDownloadData(Const.config.dataVersion)) {
                delay(250)
                if (file.exists()) file.delete()
                dbOutputStream = FileOutputStream(file)

                downloadInputStream = downloadService.downloadDatabase(Const.DB_DOWNLOAD_URL).byteStream()
                zipInputStream = ZipInputStream(downloadInputStream)
                val entry = zipInputStream.nextEntry!!
                val reader = ByteArray(8 * 1024)
                var totalRead = 0L
                var last = System.currentTimeMillis()
                val size = entry.size.toFloat()
                while (true) {
                    ensureActive()
                    val read = zipInputStream.read(reader)
                    if (read == -1) break
                    dbOutputStream.write(reader, 0, read)
                    totalRead += read
                    val current = System.currentTimeMillis()
                    if (last + 500 <= current) {
                        val progress = totalRead / size
                        setProgress(progress)
                        last = current
                    }
                }

                repo.setDataVersion(1)
            }
            setProgress(1f)
            delay(250)
            scope.launch {
                setState(current.copy(shouldRedirect = true))
            }
        } finally {
            zipInputStream?.closeEntry()
            zipInputStream?.close()
            downloadInputStream?.close()
            dbOutputStream?.close()
        }
    }
}