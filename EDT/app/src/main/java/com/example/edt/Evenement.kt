package com.example.edt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evenement_table")
data class Evenement(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "nom") val nom: String,
    @ColumnInfo(name = "creneau") val creneau: Int
)
