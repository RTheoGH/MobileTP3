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
    suspend fun getAllComptes(): List<Compte>

    @Query("SELECT * FROM compte_table WHERE login = :userLogin")
    suspend fun getCompteByLogin(userLogin: String): Compte?

    @Query("SELECT e1 FROM compte_table WHERE login = :userLogin")
    suspend fun getEvenement1(userLogin: String): String

    @Query("SELECT e2 FROM compte_table WHERE login = :userLogin")
    suspend fun getEvenement2(userLogin: String): String

    @Query("SELECT e3 FROM compte_table WHERE login = :userLogin")
    suspend fun getEvenement3(userLogin: String): String

    @Query("SELECT e4 FROM compte_table WHERE login = :userLogin")
    suspend fun getEvenement4(userLogin: String): String

    @Query("UPDATE compte_table SET e1 = :e1 WHERE login = :userLogin")
    suspend fun updateEvenement1(userLogin: String, e1: String)

    @Query("UPDATE compte_table SET e2 = :e2 WHERE login = :userLogin")
    suspend fun updateEvenement2(userLogin: String, e2: String)

    @Query("UPDATE compte_table SET e3 = :e3 WHERE login = :userLogin")
    suspend fun updateEvenement3(userLogin: String, e3: String)

    @Query("UPDATE compte_table SET e4 = :e4 WHERE login = :userLogin")
    suspend fun updateEvenement4(userLogin: String, e4: String)
}