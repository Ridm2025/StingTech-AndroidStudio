
package com.example.stingtechsolution.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.stingtechsolution.data.dao.SolicitudDao
import com.example.stingtechsolution.data.dao.UsuarioDao
import com.example.stingtechsolution.data.entity.SolicitudEntity
import com.example.stingtechsolution.data.entity.UsuarioEntity

@Database(
    entities = [
        SolicitudEntity::class,
        UsuarioEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class StingTechDatabase : RoomDatabase() {

    abstract fun solicitudDao(): SolicitudDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {

        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(
                db: SupportSQLiteDatabase
            ) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `usuarios` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `nombre` TEXT NOT NULL,
                        `correo` TEXT NOT NULL,
                        `salt` TEXT NOT NULL,
                        `hashPassword` TEXT NOT NULL
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE UNIQUE INDEX IF NOT EXISTS
                    `index_usuarios_correo`
                    ON `usuarios` (`correo`)
                    """.trimIndent()
                )
            }
        }

        @Volatile
        private var INSTANCE: StingTechDatabase? = null

        fun obtenerDatabase(
            context: Context
        ): StingTechDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    StingTechDatabase::class.java,
                    "stingtech_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
