package com.kelompok2.aplikasiplanme.room

import android.media.Image
import android.widget.ImageView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    val indisputable: String,
//    val gambar: Image,
    val inputcatatan:String,
    val tvtanggal: Int,
)