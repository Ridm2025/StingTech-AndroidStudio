
package com.example.stingtechsolution.data.security

import android.util.Base64
import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object PasswordUtils {

    private const val ITERACIONES = 120000
    private const val LONGITUD_HASH = 256

    fun generarSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)

        return Base64.encodeToString(
            bytes,
            Base64.NO_WRAP
        )
    }

    fun crearHash(
        password: String,
        salt: String
    ): String {
        val saltBytes = Base64.decode(
            salt,
            Base64.NO_WRAP
        )

        val caracteres = password.toCharArray()

        val especificacion = PBEKeySpec(
            caracteres,
            saltBytes,
            ITERACIONES,
            LONGITUD_HASH
        )

        return try {
            val factory = SecretKeyFactory.getInstance(
                "PBKDF2WithHmacSHA256"
            )

            val hash = factory.generateSecret(
                especificacion
            ).encoded

            Base64.encodeToString(
                hash,
                Base64.NO_WRAP
            )
        } finally {
            especificacion.clearPassword()
            caracteres.fill('\u0000')
        }
    }

    fun verificar(
        password: String,
        salt: String,
        hashGuardado: String
    ): Boolean {
        val hashCalculado = crearHash(
            password,
            salt
        )

        return MessageDigest.isEqual(
            Base64.decode(hashCalculado, Base64.NO_WRAP),
            Base64.decode(hashGuardado, Base64.NO_WRAP)
        )
    }
}
