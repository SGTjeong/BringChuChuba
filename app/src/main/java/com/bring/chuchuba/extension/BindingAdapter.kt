package com.bring.chuchuba.extension

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("missionStatus")
internal fun setStatus(view: TextView, status: String) {
    when(status){
        "todo" -> view.text = "시작 가능"
        "inProgress" -> view.text = "진행 중"
        "complete" -> view.text = "완료"
        else -> view.text = "알 수 없음"
    }
}