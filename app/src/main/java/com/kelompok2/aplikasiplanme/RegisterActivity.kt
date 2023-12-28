package com.kelompok2.aplikasiplanme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kelompok2.aplikasiplanme.databinding.ActivityLoginBinding
import com.kelompok2.aplikasiplanme.databinding.ActivityRegisterBinding
import io.reactivex.Observable

@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menghapus title project pada bagian atas
        supportActionBar?.hide()


        // Auth
        auth = FirebaseAuth.getInstance()

        // Fullname Validation
        val nameStream = RxTextView.textChanges(binding.fullname)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameExistAlert(it)
        }

        // Email Validation
        val emailStream = RxTextView.textChanges(binding.inputemail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidAlert(it)
        }

        // Username Validation
        val usernameStream = RxTextView.textChanges(binding.inputusername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }
        usernameStream.subscribe{
            showTextMinimalAlert(it, "Username")
        }

        // Password Validation
        val passwordStream = RxTextView.textChanges(binding.inputpasssword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe{
            showTextMinimalAlert(it, "Password")
        }

        // Button benar atau salah
        val invalidFieldsStream = Observable.combineLatest(
            nameStream,
            emailStream,
            usernameStream,
            passwordStream,
            { nameInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, passwordInvalid: Boolean ->
                !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                // Mengaktifkan tombol jika semua kolom benar
                binding.signup.isEnabled = true
                binding.signup.backgroundTintList = ContextCompat.getColorStateList(this, R.color.ungu)
            } else {
                // Nonaktifkan tombol jika salah satu kolom salah
                binding.signup.isEnabled = false
                binding.signup.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.black)
            }
        }

        binding.signup.setOnClickListener {
            val email = binding.inputemail.text.toString().trim()
            val password = binding.inputpasssword.text.toString().trim()
            registerUser(email, password)
        }
        binding.kliklogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    // Menampilkan pesan peringatan
    private fun showNameExistAlert(isNotValid: Boolean) {
        binding.fullname.error = if (isNotValid) "Nama tidak boleh kosong!" else null
    }
    // Menampilkan pesan peringatan
    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Username")
            binding.inputusername.error = if (isNotValid) "$text harus lebih dari 6 huruf!" else null
        else if (text == "Password")
            binding.inputpasssword.error = if (isNotValid) "$text harus lebih dari 8 huruf!" else null
    }

    // Menampilkan pesan peringatan
    private fun showEmailValidAlert(isNotValid: Boolean) {
        binding.inputemail.error = if (isNotValid) "Email tidak valid" else null
    }

    // Mendaftarkan usesr pada firebase menggunakan email dan password
    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}