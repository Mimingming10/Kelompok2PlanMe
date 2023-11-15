package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kelompok2.aplikasiplanme.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var db:DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        db = DatabaseHelper(this)
        val list = db.getUserAccount()
        Log.e("00000", "OnCreate: ${list.size}")

        binding.imageAccount.setOnClickListener{
            startActivity(Intent(this, AccountActivity::class.java))
        }
    }
}
