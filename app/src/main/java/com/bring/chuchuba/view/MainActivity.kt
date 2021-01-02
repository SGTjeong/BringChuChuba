package com.bring.chuchuba.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.*
import com.bring.chuchuba.NetworkManager.retrofit
import com.bring.chuchuba.databinding.ActivityMainBinding
import com.bring.chuchuba.model.Member
import com.bring.chuchuba.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = "로그 ${this.javaClass.simpleName}"

    private lateinit var binding : ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val service: MemberService by lazy {
        retrofit.create(MemberService::class.java)
    }
    private lateinit var mainViewModel : MainViewModel

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
        if (firebaseAuth.currentUser != null) {
            getMemberId()
        } else {
            signInAnonymously()
        }
    }

    private fun signInAnonymously() {
        Log.d(TAG, "signInAnonymously")

        firebaseAuth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:success")
                    getMemberId()
                } else {
                    Log.e(TAG, "signInAnonymously:failure", task.exception)
                    this@MainActivity.showToast("SignInAnonymously failed")
                }
            }
    }

    private fun getMemberId() {
        Log.d(TAG, "getMemberId")

        service.getMyInfo().enqueue(object : Callback<Member.MemberGetResult> {
            override fun onResponse(call: Call<Member.MemberGetResult>, response: Response<Member.MemberGetResult>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "response is successful\nMember uid : ${response.body()!!.uid}")
                    this@MainActivity.showToast("Member uid : ${response.body()!!.uid}")
                } else {
                    Log.e(TAG, "response is not successful")
                    this@MainActivity.showToast("registerWithToken response failed : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Member.MemberGetResult>, t: Throwable) {
                Log.e(TAG, "response failed : ", t)
                this@MainActivity.showToast("registerWithToken response failed")
            }
        })

    }
}