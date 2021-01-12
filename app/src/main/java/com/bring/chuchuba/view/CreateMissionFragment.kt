package com.bring.chuchuba.view

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.R
import com.bring.chuchuba.databinding.CreateMissionFragmentBinding
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.HomeViewModel
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import java.text.SimpleDateFormat
import java.util.*


class CreateMissionFragment : Fragment() {

    private val TAG: String = "로그"
    var title = ""
    var description = ""
    var reward = ""
    var expireAt : MutableLiveData<String> = MutableLiveData()
    private lateinit var binding: CreateMissionFragmentBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(
            requireActivity(),
            HomeInjector().provideViewModelFactory()
        ).get(
            HomeViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_mission_fragment, container, false)

        binding.cmFrag = this
        binding.hVm = homeViewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    fun createExprieAtData(){
        val dialogView = View.inflate(activity, R.layout.date_time_picker, null)
        val setButton = dialogView.findViewById<View>(R.id.date_time_set)
        val alertDialog: AlertDialog = AlertDialog.Builder(activity).create()

        setButton.setOnClickListener {
            val datePicker = dialogView.findViewById<DatePicker>(R.id.date_picker)
            val timePicker = dialogView.findViewById<TimePicker>(R.id.time_picker)
            val calendar: Calendar = GregorianCalendar(
                datePicker.year,
                datePicker.month,
                datePicker.dayOfMonth,
                timePicker.currentHour,
                timePicker.currentMinute
            )
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val time = sdf.format(Date(calendar.timeInMillis))
            Log.d(TAG, "CreateMissionFragment ~ createExprieAtData() called $time")
            expireAt.value = time
            alertDialog.dismiss()
        }
        alertDialog.setView(dialogView)
        alertDialog.show()
    }

    fun submitMission() {
        if (title != "" && description != "" && reward != "" && expireAt.value != "") {
            homeViewModel.handleEvent(
                HomeEvent.OnCreateMission(
                    title, description, reward, expireAt.toString()
                )
            )
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        } else this.showToast("빈칸이 있습니다")
    }
}