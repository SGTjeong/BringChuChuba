package com.bring.chuchuba.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.R
import com.bring.chuchuba.databinding.FragmentMyInfoBinding
import com.bring.chuchuba.extension.CreateFamilyDialog
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.HomeViewModel

class MyInfoFragment : Fragment() {

    private lateinit var binding : FragmentMyInfoBinding
    private lateinit var homeViewModel : HomeViewModel
    var fId : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            homeViewModel = ViewModelProvider(
                this,
                HomeInjector().provideViewModelFactory()
            ).get(
                HomeViewModel::class.java
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_info, container, false)

        binding.infoFrag = this
        binding.lifecycleOwner = this

        return binding.root
    }

    fun createFamily() {
        val dlg = CreateFamilyDialog(requireContext()) { familyName ->
            if (familyName != "") {
                homeViewModel.handleEvent(HomeEvent.OnCreateFamily(familyName))
//                { response ->
//                    if (response == "") this.showToast("가족 만들기 실패!")
//                    else this.showToast("\"${response}\"방에 입장했습니다!")
//                })
            } else {
                this.showToast("빈칸입니다")
            }
        }
        dlg.show()
    }

    /**
     * 가족 아이디로 조인하는 함수. 딥링크로 호출? 예정
     */
    fun joinFamily(){
        if (fId != ""){
            homeViewModel.handleEvent(HomeEvent.OnJoinFamily(fId))
//            { response ->
//                if (response == "") this.showToast("가족 입장 실패!")
//                else this.showToast("\"${response}\"방에 입장했습니다!")
//            })
        } else {
            this.showToast("check!")
        }
    }

}