package com.kelompok2.aplikasiplanme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kelompok2.aplikasiplanme.databinding.ActivityHomeBinding

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_account)
    }
}