package com.qstack.maptos.aptos

import cash.z.ecc.android.bip39.Mnemonics.MnemonicCode
import cash.z.ecc.android.bip39.Mnemonics.WordCount
import cash.z.ecc.android.bip39.toSeed
import xyz.mcxross.kaptos.Aptos
import xyz.mcxross.kaptos.account.Account
import xyz.mcxross.kaptos.core.crypto.KeyPair
import xyz.mcxross.kaptos.core.crypto.generateKeypair
import xyz.mcxross.kaptos.model.SigningSchemeInput
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey
import org.bouncycastle.crypto.generators.Ed25519KeyPairGenerator
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters
import xyz.mcxross.kaptos.core.crypto.Secp256k1PrivateKey
import xyz.mcxross.kaptos.model.HexInput


object WalletManager {

    fun generateAccount(): KeyPair {
        val account = Account.fromPrivateKey(generatePrivateKey(" "))
        return generateKeypair(SigningSchemeInput.Ed25519)
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

    private fun generateSeed(phrase: String): ByteArray {
        return MnemonicCode(phrase).toSeed()
    }

}