package com.rspk.internproject.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class CryptoManager {

    private fun getKey(): SecretKey {
        val keyStore = KeyStore.getInstance("AndroidKeyStore")
        keyStore.load(null)
        val keyEntry = keyStore.getEntry("my_secure_key_alias", null) as? KeyStore.SecretKeyEntry
        return keyEntry?.secretKey ?: createKey()
    }


    private fun createKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(ALGORITHM, "AndroidKeyStore")
        keyGenerator.init(
            KeyGenParameterSpec.Builder(
                "my_secure_key_alias", // Key alias
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(BLOCK_MODE)
                .setEncryptionPaddings(PADDING)
                .setUserAuthenticationRequired(false)
                .build()
        )
        return keyGenerator.generateKey()
    }


    fun encrypt(input: String): ByteArray {
        val encryptCipher = Cipher.getInstance(TRANSFORMATION)
        encryptCipher.init(Cipher.ENCRYPT_MODE, getKey())

        val encryptedBytes = encryptCipher.doFinal(input.toByteArray())
        val iv = encryptCipher.iv

        val output = ByteArray(iv.size + encryptedBytes.size + 1)
        output[0] = iv.size.toByte()
        System.arraycopy(iv, 0, output, 1, iv.size)
        System.arraycopy(encryptedBytes, 0, output, iv.size + 1, encryptedBytes.size)

        return output
    }

    fun decrypt(encryptedData: ByteArray): String {
        val decryptCipher = Cipher.getInstance(TRANSFORMATION)

        val ivSize = encryptedData[0].toInt()
        val iv = ByteArray(ivSize)
        System.arraycopy(encryptedData, 1, iv, 0, ivSize)
        val encryptedBytes = ByteArray(encryptedData.size - ivSize - 1)
        System.arraycopy(encryptedData, ivSize + 1, encryptedBytes, 0, encryptedBytes.size)

        val ivParams = IvParameterSpec(iv)
        decryptCipher.init(Cipher.DECRYPT_MODE, getKey(), ivParams)

        val decryptedBytes = decryptCipher.doFinal(encryptedBytes)
        return String(decryptedBytes, StandardCharsets.UTF_8)
    }


    companion object {
        private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
        private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
        private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
        private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"
    }
}