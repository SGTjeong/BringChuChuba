package com.bring.chuchuba.extension.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.bring.chuchuba.R

class FamilyAndNickNameDialog : Dialog {
    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)
    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener?) : super(context, cancelable, cancelListener)
    constructor(context : Context, callback : (String) -> Unit) : super(context){
        this.callback = callback
    }
    constructor(context : Context, myNickName : String?, callback : (String) -> Unit) : super(context){
        this.callback = callback
        this.myNickName = myNickName ?: "새로운 참가자"
    }

    private var callback : (String) -> Unit = {}
    private lateinit var myNickName : String
    private lateinit var cancelBtn : Button
    private lateinit var sendBtn : Button
    private lateinit var editText : EditText
    private val dialogTitle : TextView by lazy {
        findViewById(R.id.dialog_title)
    }
    private val dialogDescription : TextView by lazy {
        findViewById(R.id.dialog_description)
    }
    
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
        setContentView(R.layout.name_submit_dialog)
        cancelBtn = findViewById(R.id.closeNameSubmit)
        sendBtn = findViewById(R.id.sendNameSubmit)
        editText = findViewById(R.id.nickNameEditText)
        if (this::myNickName.isInitialized)
            changeNickNameDialog()
        else
            createFamilyDialog()
    }

    private fun changeNickNameDialog() {
        editText.setText(myNickName)
        cancelBtn.setOnClickListener {
            dismiss()
        }
        sendBtn.setOnClickListener {
            editText.text?:return@setOnClickListener
            callback(editText.text!!.toString())
            if (editText.text!!.toString()!="") dismiss()
        }
    }

    private fun createFamilyDialog() {
        dialogTitle.text = "가족 이름"
        dialogDescription.text = "가족 이름을 설정하고 만들어보세요"
        sendBtn.text = "만들기"
        cancelBtn.setOnClickListener {
            dismiss()
        }
        sendBtn.setOnClickListener {
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