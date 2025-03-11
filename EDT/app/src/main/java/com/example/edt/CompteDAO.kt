package com.example.edt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CompteDAO {
    @Insert
    suspend fun insert(compte: Compte)

    @Update
    suspend fun update(compte: Compte)

    @Delete
    suspend fun delete(compte: Compte)

    @Query("SELECT * FROM compte_table")
    fun getAllUsers(): List<Compte>

    @Query("SELECT * FROM compte_table WHERE login = :userLogin")
    fun getUserByLogin(userLogin: Int): Compte?
}