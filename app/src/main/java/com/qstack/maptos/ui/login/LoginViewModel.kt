package com.qstack.maptos.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qstack.maptos.aptos.KeystoreHelper
import com.qstack.maptos.aptos.WalletManager
import com.qstack.maptos.aptos.room.Wallet
import com.qstack.maptos.aptos.room.WalletRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.math.ec.FixedPointCombMultiplier
import org.bouncycastle.util.encoders.Hex
import java.math.BigInteger
import java.security.Security


class LoginViewModel : ViewModel() {


    fun createAccount(context : Context, phrase: String?=null) {
        val walletRepository = WalletRepository(context)
        viewModelScope.launch{
            val count = walletRepository.getWalletCount()
            val mnemonic = phrase ?: WalletManager.generateMnemonic()
            val privateKey = WalletManager.generatePrivateKey(mnemonic)
            val wallet = Wallet(
                walletName = "Wallet $count",
                accountName = "Account 1",
                address = WalletManager.getAddress(privateKey),
                privateKey = KeystoreHelper.encryptPrivateKey(privateKey.toString()),
                isBackedUp = false,
                mnemonic = KeystoreHelper.encryptPrivateKey(mnemonic),
                network = "Aptos"
            )
            walletRepository.insertWallet(wallet)
        }
    }

    fun checkMnemonics(phrase: String): Boolean {
        try {
            WalletManager.checkMnemonics(phrase)
            return true;
        } catch (e: Exception) {
            return false;
        }
    }

    fun isValidPrivateKey(privateKey: String): Boolean {
        return try {
            // Step 1: Check if the private key is a valid hex string of correct length
            if (privateKey.length != 64) return false
            val privKeyBigInt = BigInteger(privateKey, 16)

            // Step 2: Ensure the private key is within the valid range (1, n-1)
            val curveParams = org.bouncycastle.jce.ECNamedCurveTable.getParameterSpec("secp256k1")
            val n = curveParams.n
            if (privKeyBigInt <= BigInteger.ONE || privKeyBigInt >= n.subtract(BigInteger.ONE)) {
                return false
            }

            // Step 3: Generate the public key to verify the private key
            Security.addProvider(BouncyCastleProvider())
            val ecSpec = org.bouncycastle.jce.ECNamedCurveTable.getParameterSpec("secp256k1")
            val point = FixedPointCombMultiplier().multiply(ecSpec.g, privKeyBigInt)
            val pubKey = Hex.toHexString(point.getEncoded(false))

            // If the public key can be generated, the private key is valid
            true
        } catch (e: Exception) {
            false
        }
    }

}