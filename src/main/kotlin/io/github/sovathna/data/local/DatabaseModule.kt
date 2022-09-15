package io.github.sovathna.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.github.sovathna.domain.localdb.LocalDatabase
import io.github.sovathna.domain.wordsdb.WordsDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val databaseModule = module {
    single {
        val file = get<File>(qualifier = named("database_file"))
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePath}")
        println("words version ${WordsDatabase.Schema.version}")
        WordsDatabase(driver)
    }

    single {
        val file = get<File>(qualifier = named("local_database_file"))
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePath}")
        println("local version ${LocalDatabase.Schema.version}")
        LocalDatabase.Schema.create(driver)
        LocalDatabase(driver)
    }
}