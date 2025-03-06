package com.example.edt

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "compte_table")
data class Compte(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "mdp") val mdp: String,
    @ColumnInfo(name = "nom") val nom: String,
    @ColumnInfo(name = "prenom") val prenom: String,
    @ColumnInfo(name = "telephone") val telephone: String,
    @ColumnInfo(name = "mail") val mail: String
    //@ColumnInfo(name = "interets") val interets: List<String>
)
