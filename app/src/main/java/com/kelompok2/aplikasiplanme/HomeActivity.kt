package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.view.animation.AnimationUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kelompok2.aplikasiplanme.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var addButton: FloatingActionButton
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menghapus title project pada bagian atas
        supportActionBar?.hide()


        addButton = findViewById(R.id.tambah)

        addButton.setOnClickListener {
            toggleFabAnimation()
        }
        binding.tambah.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            startActivity(intent)
        }
    }


    private fun toggleFabAnimation() {
        val rotateOpen = AnimationUtils.loadAnimation(this, R.anim.fab_rotate)
        val rotateClose = AnimationUtils.loadAnimation(this, R.anim.fab_rotate_anticlock)
        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        if (isOpen) {
            addButton.startAnimation(rotateClose)
            // Tambahkan logika lain yang diinginkan saat tombol ditutup
        } else {
            addButton.startAnimation(rotateOpen)
            // Tambahkan logika lain yang diinginkan saat tombol dibuka
        }

        isOpen = !isOpen
    }
}

