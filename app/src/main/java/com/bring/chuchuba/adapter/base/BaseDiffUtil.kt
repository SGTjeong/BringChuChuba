package com.bring.chuchuba.adapter.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

/**
 * T 타입을 일단 == 연산자로 비교하면 속도가 안나올거 같은데
 * 그렇다고 데이터 형식마다 DiffUtill을 만들면 너무 많아질거같고..
 */
abstract class BaseDiffUtil<T> : DiffUtil.ItemCallback<T>() {

}