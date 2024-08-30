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

class LoginViewModel : ViewModel() {


    fun createAccount(context : Context) {
        val walletRepository = WalletRepository(context)
        viewModelScope.launch{
            val count = walletRepository.getWalletCount()
            val mnemonic = WalletManager.generateMnemonic()
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
}