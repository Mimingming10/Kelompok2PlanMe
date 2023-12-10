package com.kelompok2.aplikasiplanme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kelompok2.aplikasiplanme.databinding.ActivityLoginBinding
import io.reactivex.Observable

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        // Button Enable True or False
        val invalidFieldsStream = Observable.combineLatest(
            usernameStream,
            passwordStream,
            { usernameInvalid: Boolean, passwordInvalid: Boolean ->
                !usernameInvalid && !passwordInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                binding.btnlogin.isEnabled = true
                binding.btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, R.color.ungu)
            } else {
                binding.btnlogin.isEnabled = false
                binding.btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.black)
            }
        }

        binding.btnlogin.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
        binding.klikregister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        if (text == "Username")
            binding.textinputUsername.error = if (isNotValid) "$text tidak boleh kosong!" else null
        else if (text == "Password")
            binding.textinputPassword.error = if (isNotValid) "$text tidak boleh kosong!" else null
    }
}