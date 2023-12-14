package com.kelompok2.aplikasiplanme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.view.animation.AnimationUtils
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeActivity : AppCompatActivity() {
    private lateinit var addButton: FloatingActionButton
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        addButton = findViewById(R.id.tambah)

        addButton.setOnClickListener {
            toggleFabAnimation()
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

