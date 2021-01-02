package com.bring.chuchuba.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bring.chuchuba.model.MissionList
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val TAG: String = "로그 + ${this.javaClass.simpleName}"

    private val _missionData = MutableLiveData<List<MissionList>>()
    val missionData: LiveData<List<MissionList>> = _missionData

    fun getMission(){
        Log.d(TAG, "MainViewModel ~ getMission() called")
        viewModelScope.launch {

        }
    }

    fun addMission(){
        Log.d(TAG, "MainViewModel ~ addMission() called")
        viewModelScope.launch {

        }
    }

}