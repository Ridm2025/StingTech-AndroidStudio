
package com.example.stingtechsolution.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stingtechsolution.data.entity.SolicitudEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolicitudDao {

    @Insert
    suspend fun insertarSolicitud(
        solicitud: SolicitudEntity
    )

    @Query("SELECT * FROM solicitudes ORDER BY id DESC")
    fun obtenerSolicitudes(): Flow<List<SolicitudEntity>>

    @Query("DELETE FROM solicitudes WHERE id = :id")
    suspend fun eliminarSolicitud(id: Int)
}
