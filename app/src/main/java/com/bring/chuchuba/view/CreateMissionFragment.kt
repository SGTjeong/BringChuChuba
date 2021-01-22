package com.bring.chuchuba.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.bring.chuchuba.BaseFragment
import com.bring.chuchuba.FragmentLayout
import com.bring.chuchuba.databinding.CreateMissionFragmentBinding
import com.bring.chuchuba.extension.dialog.SetExpireAtDialog
import com.bring.chuchuba.extension.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent


class CreateMissionFragment : BaseFragment<CreateMissionFragmentBinding>(FragmentLayout.CreateMission) {

    private val TAG: String = "로그"
    var title = ""
    var description = ""
    var reward = ""
    var expireAt : MutableLiveData<String> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.cmFrag = this
        binding.hVm = homeViewModel
        return view
    }

    fun createExprieAtData(){
        val dialog  = SetExpireAtDialog(requireContext()){ time ->
            expireAt.value = time
        }
        dialog.show()
    }

    fun submitMission() {
        if (title != "" && description != "" && reward != "" && expireAt.value.toString() != "") {
            homeViewModel.handleEvent(
                HomeEvent.OnCreateMission(
                    title, description, reward, expireAt.value.toString()
                )
            )
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        } else this.showToast("빈칸이 있습니다")
    }
}