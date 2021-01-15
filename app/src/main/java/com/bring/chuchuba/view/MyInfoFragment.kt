package com.bring.chuchuba.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bring.chuchuba.BaseFragment
import com.bring.chuchuba.FragmentLayout
import com.bring.chuchuba.databinding.FragmentMyInfoBinding
import com.bring.chuchuba.extension.dialog.FamilyAndNickNameDialog
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent

class MyInfoFragment : BaseFragment<FragmentMyInfoBinding>(FragmentLayout.MyInfo) {

    var fId : String = ""
    var myNickname : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.infoFrag = this
        observeViewModels()
        return view
    }

    fun createFamily() {
        val dlg = FamilyAndNickNameDialog(requireContext()) { familyName ->
            if (familyName != "") {
                homeViewModel.handleEvent(HomeEvent.OnCreateFamily(familyName))
            } else {
                this.showToast("빈칸입니다")
            }
        }
        dlg.show()
    }

    private fun observeViewModels() {
        homeViewModel.myInfo.observe(viewLifecycleOwner){ member ->
            member.nickname ?: return@observe
            myNickname = member.nickname
        }
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

    fun changeNickname(){
        val dialog = FamilyAndNickNameDialog(requireContext(), myNickname){ nick->
            if (nick == "" && nick == myNickname) {
                this.showToast("빈칸이거나 중복입니다")
            }else{
                homeViewModel.handleEvent(HomeEvent.OnChangeNickname(nick))
            }
        }
        dialog.show()
    }

}