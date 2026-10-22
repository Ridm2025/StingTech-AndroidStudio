
package com.example.stingtechsolution.data.repository

import com.example.stingtechsolution.data.dao.UsuarioDao
import com.example.stingtechsolution.data.entity.UsuarioEntity
import com.example.stingtechsolution.data.security.PasswordUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class UsuarioRepository(
    private val usuarioDao: UsuarioDao
) {

    suspend fun registrar(
        nombre: String,
        correo: String,
        password: String
    ): Boolean = withContext(Dispatchers.Default) {

        val correoNormalizado =
            correo.trim().lowercase(Locale.ROOT)

        if (
            usuarioDao.buscarPorCorreo(correoNormalizado)
            != null
        ) {
            return@withContext false
        }

        val salt = PasswordUtils.generarSalt()

        val hash = PasswordUtils.crearHash(
            password,
            salt
        )

        val usuario = UsuarioEntity(
            nombre = nombre.trim(),
            correo = correoNormalizado,
            salt = salt,
            hashPassword = hash
        )

        usuarioDao.insertarUsuario(usuario)

        true
    }

    suspend fun ingresar(
        correo: String,
        password: String
    ): Boolean = withContext(Dispatchers.Default) {

        val usuario = usuarioDao.buscarPorCorreo(
            correo.trim().lowercase(Locale.ROOT)
        ) ?: return@withContext false

        PasswordUtils.verificar(
            password = password,
            salt = usuario.salt,
            hashGuardado = usuario.hashPassword
        )
    }
    suspend fun obtenerUsuario(correo: String): UsuarioEntity? {
        return usuarioDao.buscarPorCorreo(
            correo.trim().lowercase(Locale.ROOT)
        )
    }
}
