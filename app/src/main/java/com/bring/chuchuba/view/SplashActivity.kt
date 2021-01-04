package com.bring.chuchuba.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bring.chuchuba.R
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            startMainActivity()
        } else {
            signInAnonymously()
        }
    }

    private fun signInAnonymously() {
        firebaseAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:success")
                    startMainActivity()
                } else {
                    Log.e(TAG, "signInAnonymously:failure", task.exception)
                }
            }
    }

    private fun startMainActivity(){
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish() }, 2000
        )
    }
}