package com.kelompok2.aplikasiplanme

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.kelompok2.aplikasiplanme.Utils.showToast
import com.kelompok2.aplikasiplanme.databinding.AccountDialogBinding
import com.kelompok2.aplikasiplanme.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth
    private var userType : String = ""
    private var userImageUri : Uri? = null
    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        userImageUri = it
        binding.imageView.setImageURI(userImageUri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Click
        binding.apply {
            imageView.setOnClickListener {
                selectImage.launch("image/*")
            }
            binding.radioGroup.setOnCheckedChangeListener{_, checkedId ->
                userType = findViewById<RadioButton>(checkedId).text.toString()
                Log.d("TT", userType)
            }
            signup.setOnClickListener { createUser() }
            kliklogin.setOnClickListener{
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
//
//            }
        }
    }

    private fun createUser() {
        Utils.showDialog(this)

        val name = binding.inputname.text.toString()
        val email = binding.inputemail.text.toString()
        val password = binding.inputpasssword.text.toString()
        val confirmPassword = binding.inputpasssword2.text.toString()

        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
            if (userImageUri == null){
                Utils.showToast(this, "Tolong pilih salah satu gambar")
            }else if (password == confirmPassword) {
                if (userType != "")
                    uploadImageUri(name, email, password)
                else{
                    Utils.hideDialog()
                    Utils.showToast(this, "Tolong piih tipe pekerjaan!!")
                }
            }else {
                Utils.showToast(this, "Password tidak sama")
            }
        } else {
            Utils.hideDialog()
            Utils.showToast(this, "Tidak boleh ada kolom yang kosong!!")
        }
    }

    private fun saveUserData(name: String, email: String, password: String, downloadURL: Uri) {
        if(userType == "Kerja"){
            lifecycleScope.launch {
                val db = FirebaseDatabase.getInstance().getReference("Users")
                try {
                    val firebaseAuth = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
                    if(firebaseAuth.user != null){
                        FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnSuccessListener {
                            val dialog = AccountDialogBinding.inflate(LayoutInflater.from(this@RegisterActivity))
                            val alertDialog = AlertDialog.Builder(this@RegisterActivity)
                                .setView(dialog.root)
                                .create()
                            Utils.hideDialog()
                            alertDialog.show()
                            dialog.btnOk.setOnClickListener{
                                alertDialog.dismiss()
                                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                                finish()
                            }
                        }
                        val uId = firebaseAuth.user?.uid.toString()
                        val saveUserType = if(userType == "Kerja") "Kerja" else "Kuliah"
                        val kerja = Users(saveUserType,uId,name,email,password,downloadURL.toString())
                        db.child(uId).setValue(kerja).await()
                    }else{
                        Utils.hideDialog()
                        Utils.showToast(this@RegisterActivity, "Register Gagal!")
                    }
                }
                catch(e : Exception){
                    Utils.hideDialog()
                    Utils.showToast(this@RegisterActivity, e.message.toString())
                }
            }
        }
    }

    private fun uploadImageUri(name: String, email: String, password: String) {
        val currentUserid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storageReference = FirebaseStorage.getInstance().getReference("Profile").child(currentUserid).child("Profile.jpg")

        lifecycleScope.launch {
            try {
                val uploadTask = storageReference.putFile(userImageUri!!).await()
                if(uploadTask.task.isSuccessful){
                    val downloadURL = storageReference.downloadUrl.await()
                    saveUserData(name,email,password,downloadURL)
                }else{
                    Utils.hideDialog()
                    showToast("Upload gagal: ${uploadTask.task.exception?.message}")
                }
            } catch (e : Exception) {
                Utils.hideDialog()
                showToast("Upload gagal: ${e.message}}")
            }
        }
    }

    private fun showToast(message: String){
        runOnUiThread{
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}
//
//        // Auth
//        auth = FirebaseAuth.getInstance()
//
//        // Fullname Validation
//        val nameStream = RxTextView.textChanges(binding.fullname)
//            .skipInitialValue()
//            .map { name ->
//                name.isEmpty()
//            }
//        nameStream.subscribe {
//            showNameExistAlert(it)
//        }
//
//        // Email Validation
//        val emailStream = RxTextView.textChanges(binding.inputemail)
//            .skipInitialValue()
//            .map { email ->
//                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
//            }
//        emailStream.subscribe {
//            showEmailValidAlert(it)
//        }
//
//        // Username Validation
//        val usernameStream = RxTextView.textChanges(binding.inputusername)
//            .skipInitialValue()
//            .map { username ->
//                username.length < 6
//            }
//        usernameStream.subscribe{
//            showTextMinimalAlert(it, "Username")
//        }
//
//        // Password Validation
//        val passwordStream = RxTextView.textChanges(binding.inputpasssword)
//            .skipInitialValue()
//            .map { password ->
//                password.length < 8
//            }
//        passwordStream.subscribe{
//            showTextMinimalAlert(it, "Password")
//        }
//
//        // Button Enable True or False
//        val invalidFieldsStream = Observable.combineLatest(
//            nameStream,
//            emailStream,
//            usernameStream,
//            passwordStream,
//            { nameInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean, passwordInvalid: Boolean ->
//                !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid
//            })
//        invalidFieldsStream.subscribe { isValid ->
//            if (isValid) {
//                binding.signup.isEnabled = true
//                binding.signup.backgroundTintList = ContextCompat.getColorStateList(this, R.color.ungu)
//            } else {
//                binding.signup.isEnabled = false
//                binding.signup.backgroundTintList = ContextCompat.getColorStateList(this, android.R.color.black)
//            }
//        }
//
//
//    }
//
//    private fun showNameExistAlert(isNotValid: Boolean) {
//        binding.fullname.error = if (isNotValid) "Nama tidak boleh kosong!" else null
//    }
//    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
//        if (text == "Username")
//            binding.inputusername.error = if (isNotValid) "$text harus lebih dari 6 huruf!" else null
//        else if (text == "Password")
//            binding.inputpasssword.error = if (isNotValid) "$text harus lebih dari 8 huruf!" else null
//    }
//
//    private fun showEmailValidAlert(isNotValid: Boolean) {
//        binding.inputemail.error = if (isNotValid) "Email tidak valid" else null
//    }
//
//    private fun registerUser(email: String, password: String) {
//        auth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener(this) {
//                if (it.isSuccessful) {
//                    startActivity(Intent(this, LoginActivity::class.java))
//                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
//                }
//            }
//    }
// }