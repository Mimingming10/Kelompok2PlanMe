package com.kelompok2.aplikasiplanme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kelompok2.aplikasiplanme.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.klikdisini.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
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

           }else{
               Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
           }
        }else{
            Toast.makeText(this, "Tolong inputkan semua", Toast.LENGTH_SHORT).show()
        }
    }


}