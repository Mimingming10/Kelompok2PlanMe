package com.kelompok2.aplikasiplanme

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser?.uid
            if(currentUser != null){
                lifecycleScope.launch {
                    try {
                        FirebaseDatabase.getInstance().getReference("Users").child(currentUser).addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val currentUserData = snapshot.getValue(Users::class.java)
                                when(currentUserData?.userType){
                                    "Kerja" -> {
                                        startActivity(Intent(this@SplashActivity, KerjaMainActivity::class.java))
                                        finish()
                                    }
                                    "Kuliah" -> {
                                        startActivity(Intent(this@SplashActivity, KuliahMainActivity::class.java))
                                        finish()
                                    }else->{
                                    startActivity(Intent(this@SplashActivity, KuliahMainActivity::class.java))
                                    finish()
                                }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Utils.hideDialog()
                                Utils.showToast(this@SplashActivity, error.message)
                            }
                        })
                    } catch (e : Exception){
                        Utils.showToast(this@SplashActivity, e.message!!)
                    }
                }
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }, 1000)

    }
}