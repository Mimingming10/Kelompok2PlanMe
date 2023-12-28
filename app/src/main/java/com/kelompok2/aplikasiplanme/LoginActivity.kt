package com.kelompok2.aplikasiplanme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kelompok2.aplikasiplanme.databinding.ActivityLoginBinding
import io.reactivex.Observable

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menghapus title project pada bagian atas
        supportActionBar?.hide()

        // Auth
        auth = FirebaseAuth.getInstance()

        // Username Validation
        val usernameStream = RxTextView.textChanges(binding.textinputUsername)
            .skipInitialValue()
            .map { username ->
                username.isEmpty()
            }
        usernameStream.subscribe{
            showTextMinimalAlert(it, "Username")
        }

        // Password Validation
        val passwordStream = RxTextView.textChanges(binding.textinputPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe{
            showTextMinimalAlert(it, "Password")
        }

        // Button benar atau salah
        val invalidFieldsStream = Observable.combineLatest(
            usernameStream,
            passwordStream,
            { usernameInvalid: Boolean, passwordInvalid: Boolean ->
                !usernameInvalid && !passwordInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                // Mengaktifkan tombol jika semua kolom benar
                binding.btnlogin.isEnabled = true
                binding.btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, R.color.ungu)
            } else {
                // Nonaktifkan tombol jika salah satu kolom salah
                binding.btnlogin.isEnabled = false
                binding.btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.black)
            }
        }

        binding.btnlogin.setOnClickListener {
            val email = binding.textinputUsername.text.toString().trim()
            val password = binding.textinputPassword.text.toString().trim()
            loginUser(email, password)
        }
        binding.klikregister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // Menampilkan pesan peringatan
    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Username")
            binding.textinputUsername.error = if (isNotValid) "$text tidak boleh kosong!" else null
        else if (text == "Password")
            binding.textinputPassword.error = if (isNotValid) "$text tidak boleh kosong!" else null
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { login ->
                if (login.isSuccessful) {
                    // Melanjutkan ke home activity dan menghapus back stack
                    Intent(this, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    }
                    // Menampilkan pesan error
                } else {
                    Toast.makeText(this, login.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}