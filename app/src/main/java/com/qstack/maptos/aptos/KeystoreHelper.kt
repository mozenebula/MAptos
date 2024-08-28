package com.qstack.maptos.aptos
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


object KeystoreHelper {
    private val alias = "APTOS"  // 固定的密钥别名
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    init {
        keyStore.load(null)
        if (!keyStore.containsAlias(alias)) {
            generateKey()
        }
    }

    // 生成密钥并存储在 Android Keystore 中
    private fun generateKey() {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()
    }

    fun encryptPrivateKey(privateKey: String): String {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = keyStore.getKey(alias, null) as SecretKey
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedKey = cipher.doFinal(privateKey.toByteArray(Charsets.UTF_8))

        val ivBase64 = Base64.encodeToString(iv, Base64.DEFAULT)
        val encryptedKeyBase64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT)
        return "$ivBase64:$encryptedKeyBase64"
    }

    fun decryptPrivateKey(encryptedString: String): String {
        val parts = encryptedString.split(":")
        if (parts.size != 2) throw IllegalArgumentException("Invalid encrypted string format")

        val iv = Base64.decode(parts[0], Base64.DEFAULT)
        val encryptedKey = Base64.decode(parts[1], Base64.DEFAULT)

        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        val secretKey = keyStore.getKey(alias, null) as SecretKey
        val gcmSpec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
        val decryptedKey = cipher.doFinal(encryptedKey)
        return String(decryptedKey, Charsets.UTF_8)
    }
}