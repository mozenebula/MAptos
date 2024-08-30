package com.qstack.maptos.aptos.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WalletDao {
    @Insert
    fun insert(wallet: Wallet)

    @Update
    fun update(wallet: Wallet)

    @Query("SELECT * FROM wallets WHERE id = :id")
    fun getWalletById(id: Long): Wallet?

    @Query("DELETE FROM wallets WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT COUNT(*) FROM wallets")
    fun getWalletCount(): Int
}