package com.bring.chuchuba.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.*
import com.bring.chuchuba.databinding.ActivityMainBinding
import com.bring.chuchuba.view.home.buildlogic.HomeEvent
import com.bring.chuchuba.view.home.buildlogic.HomeInjector
import com.bring.chuchuba.view.home.buildlogic.HomeViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        homeViewModel = ViewModelProvider(
            this,
            HomeInjector().provideViewModelFactory()
        ).get(
            HomeViewModel::class.java
        )

        firebaseAuth = FirebaseAuth.getInstance()

        observeViewModels()
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            getMyInfo()
        } else {
            signInAnonymously()
        }
    }

    private fun observeViewModels(){
        /**
         *  MainActivity is observing myInfo in ViewModel.
         *  Observer{} will be called every time the data in myInfo changes.
         * */
        homeViewModel.myInfo.observe(
            this,
            Observer { member ->
                member?:return@Observer
                showToast("member id : ${member.uid}, family id : ${member.familyId}")
            }
        )
    }
    private fun signInAnonymously() {
        Log.d(TAG, "signInAnonymously")

        firebaseAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:success")
                    getMyInfo()
                } else {
                    Log.e(TAG, "signInAnonymously:failure", task.exception)
                    this@MainActivity.showToast("SignInAnonymously failed")
                }
            }
    }

    private fun getMyInfo() {
        Log.d(TAG, "getMemberId")
        homeViewModel.handleEvent(HomeEvent.OnStart)
    }
}