package com.qstack.maptos.ui.login

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qstack.maptos.MainActivity
import com.qstack.maptos.aptos.KeystoreHelper
import com.qstack.maptos.aptos.WalletManager
import com.qstack.maptos.aptos.data.WalletInfo
import com.qstack.maptos.aptos.room.Wallet
import com.qstack.maptos.aptos.room.WalletRepository
import kotlinx.coroutines.launch
import xyz.mcxross.kaptos.core.crypto.Ed25519PrivateKey


class LoginViewModel : ViewModel() {

    private var _checkImport = MutableLiveData<Boolean>()

    val checkImport : LiveData<Boolean> get() = _checkImport

    private var _wallet = MutableLiveData<Wallet?>()

    val wallet : LiveData<Wallet?> get() = _wallet


    fun checkWallet(context: Context) {
        val walletRepository = WalletRepository(context)
        viewModelScope.launch {
            val count = walletRepository.getWalletCount()
            if(count > 0) {
                val wallet = walletRepository.getFirstWallet()
                if(wallet != null) {
                    _wallet?.postValue(wallet)
                }
            }
        }

    }


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
            _wallet.postValue(wallet)
        }
    }

    fun createAccountByPrivateKey(context: Context, priKey: String) {
        val walletRepository = WalletRepository(context)
        viewModelScope.launch{
            val count = walletRepository.getWalletCount()
            val privateKey = Ed25519PrivateKey(priKey)
            val wallet = Wallet(
                walletName = "Wallet $count",
                accountName = "Account 1",
                address = WalletManager.getAddress(privateKey),
                privateKey = KeystoreHelper.encryptPrivateKey(privateKey.toString()),
                isBackedUp = false,
                mnemonic = "",
                network = "Aptos"
            )
            walletRepository.insertWallet(wallet)
            _wallet.postValue(wallet)
        }
    }

    fun isValidMnemonics(phrase: String) {
       _checkImport.value = WalletManager.checkMnemonics(phrase)
    }

    fun isValidPrivateKey(privateKey: String) {
        _checkImport.value = WalletManager.checkPrivateKey(privateKey)
    }
}