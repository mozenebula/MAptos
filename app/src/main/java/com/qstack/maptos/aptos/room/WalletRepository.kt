package com.qstack.maptos.aptos.room

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WalletRepository(context: Context) {
    private val walletDao: WalletDao = AppDatabase.getDatabase(context).walletDao()
    private val LOGTAG = "WalletRepository"

    // 插入钱包
    suspend fun insertWallet(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            walletDao.insert(wallet)
            Log.e(LOGTAG, "Insert Wallet : $wallet")
        }
    }

    // 获取钱包
    suspend fun getWalletById(id: Long): Wallet? {
        return withContext(Dispatchers.IO) {
            walletDao.getWalletById(id)
        }
    }

    // 更新钱包
    suspend fun updateWallet(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            walletDao.update(wallet)
        }
    }

    // 删除钱包
    suspend fun deleteWalletById(id: Long) {
        withContext(Dispatchers.IO) {
            walletDao.deleteById(id)
        }
    }

    //查询钱包总数
    suspend fun getWalletCount(): Int {
        return withContext(Dispatchers.IO) {
            walletDao.getWalletCount()
        }
    }

    suspend fun getFirstWallet(): Wallet? {
        return withContext(Dispatchers.IO) {
            walletDao.getFirstWallet()
        }
    }
}
