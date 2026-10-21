package com.example.stingtechsolution.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stingtechsolution.data.dao.SolicitudDao
import com.example.stingtechsolution.data.entity.SolicitudEntity

@Database(
    entities = [SolicitudEntity::class],
    version = 1,
    exportSchema = false
)
abstract class StingTechDatabase : RoomDatabase() {

    abstract fun solicitudDao(): SolicitudDao

    companion object {

        @Volatile
        private var INSTANCE: StingTechDatabase? = null

        fun obtenerDatabase(context: Context): StingTechDatabase {

            return INSTANCE ?: synchronized(this) {

                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    StingTechDatabase::class.java,
                    "stingtech_database"
                ).build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}
