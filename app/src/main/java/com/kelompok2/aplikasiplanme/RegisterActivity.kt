package com.kelompok2.aplikasiplanme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kelompok2.aplikasiplanme.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        binding.klikdisini.setOnClickListener{
            registerUser()
        }
    }
    private fun registerUser() {
        val username = binding.inputusername.text.toString()
        val email = binding.inputemail.text.toString()
        val password = binding.inputpasssword.text.toString()

        if (ValidationUtils.isTextNotEmpty(username) &&
            ValidationUtils.isTextNotEmpty(email) &&
            ValidationUtils.isTextNotEmpty(password)
        ){
            if (ValidationUtils.isValidEmail(email)){
                val user = User(username = username, email = email.trim(), password = password)
                db.registerUser(user)
                Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_LONG).show()

            }else{
                Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this,"Tolong inputkan semua", Toast.LENGTH_SHORT).show()
        }
    }
}