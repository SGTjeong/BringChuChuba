package com.bring.chuchuba.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.*
import com.bring.chuchuba.LoginManager.firebaseAuth
import com.bring.chuchuba.databinding.ActivityMainBinding
import com.bring.chuchuba.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.vm = mainViewModel

        firebaseAuth = FirebaseAuth.getInstance()


    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.currentUser?.let {
            LoginManager.getMemberId(this@MainActivity)
        } ?: LoginManager.signInAnonymously(this@MainActivity)
    }

}