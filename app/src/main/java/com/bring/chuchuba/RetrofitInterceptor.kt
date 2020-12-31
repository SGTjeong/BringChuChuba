package com.bring.chuchuba

import android.os.Build
import android.os.LocaleList
import com.google.android.gms.tasks.Tasks
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.*

class RetrofitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        return try{
            val firebaseAuth = FirebaseAuth.getInstance()
            val user = firebaseAuth.currentUser
            if (user == null) {
                val modifiedRequest: Request = request.newBuilder()
                    .addHeader("Authorization", "null")
                    .method(request.method(), request.body())
                    .build()
                chain.proceed(modifiedRequest)
            } else {
                val task = user.getIdToken(true)
                val tokenResult = Tasks.await(task)
                val idToken = tokenResult.token
                if (idToken == null) {
                    throw Exception("idToken is null")
                } else {
                    val modifiedRequest: Request = request.newBuilder()
                        .addHeader("Authorization", "$idToken")
                        .method(request.method(), request.body())
                        .build()
                    chain.proceed(modifiedRequest)
                }
            }
        } catch (e : Exception){
            throw IOException(e.message)
        }
    }
}