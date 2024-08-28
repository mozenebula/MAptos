package com.qstack.maptos.aptos.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallets")
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // 自动生成的ID
    val walletName: String,  // 钱包名
    val accountName: String, // 账户名
    val address: String,     // 地址
    val privateKey: String,  // 私钥
    val mnemonic: String, // 助记词
    val network: String,   // 网络
    val isBackedUp: Boolean,  // 是否备份

)
