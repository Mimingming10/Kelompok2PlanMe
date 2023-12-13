package com.kelompok2.aplikasiplanme

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.internal.Util
import com.jakewharton.rxbinding2.widget.RxTextView
import com.kelompok2.aplikasiplanme.databinding.ActivityLoginBinding
import com.kelompok2.aplikasiplanme.databinding.ForgotPasswordDialogBinding
import io.reactivex.Observable
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnlogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginUser(email, password)
        }
        binding.klikregister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
        binding.lupapassword.setOnClickListener {showForgotpasswordDialog()
        }

    }

    private fun showForgotpasswordDialog() {
        val dialog = ForgotPasswordDialogBinding.inflate(LayoutInflater.from(this))
        val alertDialog = AlertDialog.Builder(this)
            .setView(dialog.root)
            .create()
        alertDialog.show()
        dialog.etEmail.requestFocus()
        dialog.tvBackToLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }
        dialog.btnForgotPassword.setOnClickListener {
            val email = dialog.etEmail.text.toString()
            alertDialog.dismiss()
            resetPassword(email)
        }
    }

    private fun resetPassword(email: String) {
        lifecycleScope.launch {
            try {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
                Utils.showToast(this@LoginActivity, "Tolong cek email Anda dan reset password")
            }
            catch (e : Exception){
                Utils.showToast(this@LoginActivity, e.message.toString())
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        Utils.showDialog(this)
        val firebaseAuth = FirebaseAuth.getInstance()
        lifecycleScope.launch {
            try{
                val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
                val currentUser = authResult.user?.uid
                val verifyEmail = firebaseAuth.currentUser?.isEmailVerified
                if(verifyEmail == true){
                    if(currentUser != null){
                        // Users, uid
                        FirebaseDatabase.getInstance().getReference("Users").child(currentUser).addListenerForSingleValueEvent(object : ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val currentUserData = snapshot.getValue(Users::class.java)
                                when(currentUserData?.userType){
                                    "Kerja" -> {
                                        startActivity(Intent(this@LoginActivity, KerjaMainActivity::class.java))
                                        finish()
                                    }
                                    "Kuliah" -> {
                                        startActivity(Intent(this@LoginActivity, KuliahMainActivity::class.java))
                                        finish()
                                    }else->{
                                    startActivity(Intent(this@LoginActivity, KuliahMainActivity::class.java))
                                    finish()
                                }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Utils.hideDialog()
                                Utils.showToast(this@LoginActivity, error.message)
                            }
                        })
                    }
                    else{
                        Utils.hideDialog()
                        Utils.showToast(this@LoginActivity, "Pengguna tidak dapat login")
                    }
                }
                else {
                    Utils.hideDialog()
                    Utils.showToast(this@LoginActivity, "Email tidak terverifikasi")
                }
            }
            catch (e : Exception){
                Utils.hideDialog()
                Utils.showToast(this@LoginActivity, e.message!!)

            }
        }
    }

//        // Auth
//        auth = FirebaseAuth.getInstance()
//
//        // Username Validation
//        val usernameStream = RxTextView.textChanges(binding.textinputUsername)
//            .skipInitialValue()
//            .map { username ->
//                username.isEmpty()
//            }
//        usernameStream.subscribe{
//            showTextMinimalAlert(it, "Username")
//        }
//
//        // Password Validation
//        val passwordStream = RxTextView.textChanges(binding.textinputPassword)
//            .skipInitialValue()
//            .map { password ->
//                password.isEmpty()
//            }
//        passwordStream.subscribe{
//            showTextMinimalAlert(it, "Password")
//        }
//
//        // Button Enable True or False
//        val invalidFieldsStream = Observable.combineLatest(
//            usernameStream,
//            passwordStream,
//            { usernameInvalid: Boolean, passwordInvalid: Boolean ->
//                !usernameInvalid && !passwordInvalid
//            })
//        invalidFieldsStream.subscribe { isValid ->
//            if (isValid) {
//                binding.btnlogin.isEnabled = true
//                binding.btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, R.color.ungu)
//            } else {
//                binding.btnlogin.isEnabled = false
//                binding.btnlogin.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.black)
//            }
//        }
//
//    }
//
//    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
//        if (text == "Username")
//            binding.textinputUsername.error = if (isNotValid) "$text tidak boleh kosong!" else null
//        else if (text == "Password")
//            binding.textinputPassword.error = if (isNotValid) "$text tidak boleh kosong!" else null
//    }

}