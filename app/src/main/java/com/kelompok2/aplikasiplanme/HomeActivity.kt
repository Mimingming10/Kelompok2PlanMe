package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupListener()
    }

    fun setupListener() {
        val tambah = findViewById<FloatingActionButton>(R.id.tambah)
        tambah.setOnClickListener{
            startActivity(Intent(this,CreateNoteActivity::class.java))
        }
    }

}

