package com.bring.chuchuba

import android.app.Activity
import android.util.Log
import com.bring.chuchuba.model.Member
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginManager {

    private val TAG: String = "로그 ${this.javaClass.simpleName}"
    lateinit var firebaseAuth: FirebaseAuth
    private val service: MemberService by lazy {
        NetworkManager.retrofit.create(MemberService::class.java)
    }

    fun signInAnonymously(activity : Activity) {
        Log.d(TAG, "signInAnonymously")

        firebaseAuth.signInAnonymously()
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInAnonymously:success")
                    getMemberId(activity)
                } else {
                    Log.e(TAG, "signInAnonymously:failure", task.exception)
                    activity.showToast("SignInAnonymously failed")
                }
            }
    }

    fun getMemberId(activity : Activity) {
        Log.d(TAG, "getMemberId")

        service.getMyInfo().enqueue(object : Callback<Member.MemberGetResult> {
            override fun onResponse(call: Call<Member.MemberGetResult>, response: Response<Member.MemberGetResult>) {
                if (response.isSuccessful) {
                    Log.d(TAG, "response is successful\nMember uid : ${response.body()!!.uid}")
                    activity.showToast("Member uid : ${response.body()!!.uid}")
                } else {
                    Log.e(TAG, "response is not successful")
                    activity.showToast("registerWithToken response failed : ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Member.MemberGetResult>, t: Throwable) {
                Log.e(TAG, "response failed : ", t)
                activity.showToast("registerWithToken response failed")
            }
        })

    }
}