package com.kelompok2.aplikasiplanme.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    val indisputable: String,
    val inputcatatan:String
)