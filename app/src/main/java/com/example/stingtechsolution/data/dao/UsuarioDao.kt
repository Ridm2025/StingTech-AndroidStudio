package com.example.stingtechsolution.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.stingtechsolution.data.entity.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertarUsuario(usuario: UsuarioEntity)

    @Query(
        "SELECT * FROM usuarios WHERE correo = :correo LIMIT 1"
    )
    suspend fun buscarPorCorreo(
        correo: String
    ): UsuarioEntity?
}
