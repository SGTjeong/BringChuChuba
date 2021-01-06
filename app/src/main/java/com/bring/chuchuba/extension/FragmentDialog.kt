package com.bring.chuchuba.extension

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.bring.chuchuba.R
import com.bring.chuchuba.databinding.MakeFamilyDialogBinding

class FragmentDialog : DialogFragment() {
    lateinit var binding : MakeFamilyDialogBinding
    private val TAG: String = "로그"

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "FragmentDialog ~ onCreateDialog() called")
        val dlg = Dialog(requireContext())
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.make_family_dialog, null, false
        )
        dlg.setContentView(binding.root)
        dlg.window?.setBackgroundDrawable((ColorDrawable(Color.TRANSPARENT)))
        return dlg
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        Log.d(TAG, "FragmentDialog ~ onCreateView() called")
//        binding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.make_family_dialog, null, false)
//
//        return binding.root
//    }

}