package com.qstack.maptos.aptos.room

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class WalletRepository(context: Context) {
    private val walletDao: WalletDao = AppDatabase.getDatabase(context).walletDao()

    // 插入钱包
    suspend fun insertWallet(wallet: Wallet) {
        withContext(Dispatchers.IO) {
            walletDao.insert(wallet)
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
}
