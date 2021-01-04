package com.bring.chuchuba

import android.app.Activity
import android.widget.Toast

internal fun Activity.showToast(msg : String){
    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
}