package com.qstack.maptos.aptos.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WalletInfo(
    val walletName: String?,  // 钱包名
    val accountName: String?, // 账户名
    val address: String?,     // 地址
    val isBackedUp: Boolean?,  // 是否备份
) : Parcelable
