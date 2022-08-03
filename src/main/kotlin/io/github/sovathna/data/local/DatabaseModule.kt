package io.github.sovathna.data.local

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.github.sovathna.domain.local.Database
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val databaseModule = module {
    single {
        val file = get<File>(qualifier = named("database_file"))
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePath}")
        Database.Schema.migrate(driver, 1, 2)
        Database(driver)
    }
}