package com.bring.chuchuba.view

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bring.chuchuba.R
import com.bring.chuchuba.databinding.FragmentMyInfoBinding
import com.bring.chuchuba.showToast
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeEvent
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeInjector
import com.bring.chuchuba.viewmodel.home.buildlogic.HomeViewModel

class MyInfoFragment : Fragment() {

    private lateinit var binding: FragmentMyInfoBinding
    private lateinit var homeViewModel: HomeViewModel

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

    /**
     * 뷰모델에게 이벤트 처리
     * 다이얼로그만 잘 처리하면...
     */
    fun createFamily() {
        val dlg = Dialog(requireContext())
        dlg.setContentView(R.layout.make_family_dialog)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dlg.show()

//        dlg.sendSubmit.setOnClickListener {
//            val familyName = dlg.familyName.text.trim().toString()
//            if (familyName != "") {
//                homeViewModel.handleEvent(HomeEvent.OnFamilyRequest(familyName))
//                dlg.dismiss()
//            } else {
//                showToast("빈칸입니다")
//            }
//        }
//        dlg.closeSubmit.setOnClickListener {
//            dlg.dismiss()
//        }
    }

}