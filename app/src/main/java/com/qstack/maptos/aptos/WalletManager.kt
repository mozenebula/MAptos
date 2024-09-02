package com.qstack.maptos.aptos

import cash.z.ecc.android.bip39.Mnemonics.MnemonicCode
import cash.z.ecc.android.bip39.Mnemonics.WordCount
import cash.z.ecc.android.bip39.toSeed
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.util.encoders.Hex
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import xyz.mcxross.kaptos.core.crypto.PrivateKey
import xyz.mcxross.kaptos.model.HexInput
import java.security.KeyFactory
import java.security.Security
import java.security.spec.PKCS8EncodedKeySpec


object WalletManager {

    fun checkPrivateKey(privateKey: String): Boolean {
        try {
            Ed25519PrivateKey(privateKey)
            return true
        }catch (e: Exception) {
            return false
        }
    }

    fun getAddress(privateKey: Ed25519PrivateKey): String {
        return Account.fromPrivateKey(privateKey).accountAddress.toString()
    }

    fun generatePrivateKey(phrase: String): Ed25519PrivateKey {
        val seed = generateSeed(phrase)
        val privateKeyParams = Ed25519PrivateKeyParameters(seed, 0)
        return  Ed25519PrivateKey(HexInput.fromByteArray(privateKeyParams.encoded))
    }

    fun generateMnemonic(): String {
        return MnemonicCode(WordCount.COUNT_12).joinToString(" ")
    }

    fun checkMnemonics(phrase: String): Boolean {
        try {
            MnemonicCode(phrase).validate()
            return true
        } catch (e : Exception) {
            return false
        }
    }

    private fun generateSeed(phrase: String): ByteArray {
        return MnemonicCode(phrase).toSeed()
    }

}