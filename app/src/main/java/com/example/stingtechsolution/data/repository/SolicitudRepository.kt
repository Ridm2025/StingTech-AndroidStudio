package com.example.stingtechsolution.data.repository

import com.example.stingtechsolution.data.dao.SolicitudDao
import com.example.stingtechsolution.data.entity.SolicitudEntity
import com.example.stingtechsolution.model.Solicitud
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SolicitudRepository(
    private val solicitudDao: SolicitudDao
) {

    val solicitudes: Flow<List<Solicitud>> =
        solicitudDao.obtenerSolicitudes().map { entidades ->
            entidades.map { entidad ->
                Solicitud(
                    id = entidad.id,
                    nombreEquipo = entidad.nombreEquipo,
                    tipoEquipo = entidad.tipoEquipo,
                    descripcion = entidad.descripcion,
                    estado = entidad.estado
                )
            }
        }

    suspend fun insertarSolicitud(
        nombreEquipo: String,
        tipoEquipo: String,
        descripcion: String
    ) {
        val solicitud = SolicitudEntity(
            nombreEquipo = nombreEquipo,
            tipoEquipo = tipoEquipo,
            descripcion = descripcion
        )

        solicitudDao.insertarSolicitud(solicitud)
    }

    suspend fun eliminarSolicitud(id: Int) {
        solicitudDao.eliminarSolicitud(id)
    }
}
