package com.bring.chuchuba

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var retrofit : Retrofit
    private lateinit var service : MemberService

    private val BASE_URL = "BASE_URL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        val httpClient = OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(RetrofitInterceptor())
                .build()

        retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(httpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        service = retrofit.create(MemberService::class.java)
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            getMemberId()
        } else {
            signInAnonymously()
        }
    }

    private fun signInAnonymously() {
        Log.e(TAG, "signInAnonymously")
        firebaseAuth.signInAnonymously()
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful) {
                        Log.e(TAG, "signInAnonymously:success")
                        getMemberId()
                    } else {
                        Log.e(TAG, "signInAnonymously:failure", task.exception)
                        this@MainActivity.showToast("SignInAnonymously failed")
                    }
                }
    }

    private fun getMemberId(){
        Log.e(TAG, "getMemberId")
        service.getMyInfo().enqueue(object : Callback<Member.MemberGetResult>{
            override fun onResponse(call: Call<Member.MemberGetResult>, response: Response<Member.MemberGetResult>) {
                if(response.isSuccessful){
                    Log.e(TAG, "response is successful")
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