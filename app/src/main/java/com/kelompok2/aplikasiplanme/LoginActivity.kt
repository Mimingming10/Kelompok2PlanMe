package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.kelompok2.aplikasiplanme.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        binding.klikregister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.button.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.textinputUsername.text.toString().trim()
        val password = binding.textinputPassword.text.toString().trim()

        if(ValidationUtils.isTextNotEmpty(email) && ValidationUtils.isTextNotEmpty(password)){
            if(ValidationUtils.isValidEmail(email)) {
                val isSuccess = db.loginUser(email, password)
                if (isSuccess) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }

            }else{
                Toast.makeText(this, "Username tidak terdaftar", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this, "Tolong inputkan semua", Toast.LENGTH_SHORT).show()
        }
    }

}