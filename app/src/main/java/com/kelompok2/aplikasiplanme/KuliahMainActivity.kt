package com.kelompok2.aplikasiplanme

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.kelompok2.aplikasiplanme.databinding.ActivityKuliahMainBinding

class KuliahMainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityKuliahMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKuliahMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tbKuliah.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logout -> {
                    showLogOutDilog()
                    true
                }else -> false

            }
        }
    }

    private fun showLogOutDilog() {
        val builder = AlertDialog.Builder(this)
        val alertDialog = builder.create()
        builder.setTitle("Log Out")
            .setMessage("Apakah Anda ingin keluar?")
            .setPositiveButton("Ya"){_,_ ->
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                this.finish()
            }
            .setNegativeButton("Tidak") { _, _ ->
                alertDialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }
}