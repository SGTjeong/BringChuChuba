package com.bring.chuchuba.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.bring.chuchuba.model.MissionList
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val TAG: String = "로그 ${this.javaClass.simpleName}"

    private val _missionData = MutableLiveData<List<MissionList>>()
    val missionData: LiveData<List<MissionList>> = _missionData

    var familyName : MutableLiveData<String> = MutableLiveData("Hello")
    var str = "null"

    fun changeFamilyName(){
        familyName.value = str
    }

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