package com.example.edt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EvenementDAO {
    @Insert
    suspend fun insert(event: Evenement)

    @Update
    suspend fun update(event: Evenement)

    @Delete
    suspend fun delete(event: Evenement)

    @Query("SELECT * FROM evenement_table")
    suspend fun getAllEvenements(): List<Evenement>

//    @Query("SELECT * FROM evenement_table WHERE id = :")
//    suspend fun getEvenementById(userLogin: String): Compte?
}