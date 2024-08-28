package com.qstack.maptos.aptos.data

data class Wallet(
    val mnemonic: String,
    val masterPrivateKey: String,
    val accounts: Map<String, Account> // 账户ID到账户的映射
)
