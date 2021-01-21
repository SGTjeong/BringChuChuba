package com.bring.chuchuba

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService

class MyFirebaseMessagingService : FirebaseMessagingService() {
    var token : String? = null
    override fun onNewToken(token: String) {
        this.token = token
        Log.d("로그", "MyFirebaseMessagingService ~ onNewToken() called ${this.token}")
    }
}