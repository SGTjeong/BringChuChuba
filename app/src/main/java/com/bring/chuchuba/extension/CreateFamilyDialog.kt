package com.bring.chuchuba.extension

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import com.bring.chuchuba.R

class CreateFamilyDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)
    constructor(context : Context, callback : (String) -> Unit) : super(context){
        this.callback = callback
    }

    private var callback : (String) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND or WindowManager.LayoutParams.FLAG_FULLSCREEN
        layoutParams.dimAmount = 0.8f
        window?.attributes = layoutParams
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        // window?.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setUpContents()
    }

    private fun setUpContents() {
        setContentView(R.layout.make_family_dialog)
        val leftBtn = findViewById<Button>(R.id.closeSubmit)
        val rightBtn = findViewById<Button>(R.id.sendSubmit)
        val editText = findViewById<EditText>(R.id.familyName)
        leftBtn.setOnClickListener {
            dismiss()
        }
        rightBtn.setOnClickListener {
            editText.text?:return@setOnClickListener
            callback(editText.text!!.toString())
            if (editText.text!!.toString()!="") dismiss()
        }
    }

    override fun show() {
        super.show()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

}