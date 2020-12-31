package com.bring.chuchuba.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bring.chuchuba.model.MissionList

class MainViewModel : ViewModel() {
    private val TAG: String = "로그 + ${this.javaClass.simpleName}"

    private val _data = MutableLiveData<List<MissionList>>()
    val data: LiveData<List<MissionList>> = _data

    fun getMission(){
        Log.d(TAG, "MainViewModel ~ getMission() called")

    }


}