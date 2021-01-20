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

    var myNickname : String? = null
    var myFamilyId : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.infoFrag = this
        binding.hvm = homeViewModel
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
            member ?: return@observe
            myNickname = member.nickname
            myFamilyId = member.familyId
        }
    }

    /**
     * 딥링크 만들기
     * 가족을 실수로 만든 상태여도 JoinFamily를 호출하면 ->
     * 가족을 삭제하고 참여할지, 선택하는 창을 만들면 좋을듯
     */
    fun joinFamily(){
        if (myFamilyId!=null){
            homeViewModel.handleEvent(HomeEvent.OnCreateFamilyLink)
        } else {
            this.showToast("먼저 가족을 만드세요")
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