package com.bring.chuchuba.adapter

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bring.chuchuba.adapter.base.BaseViewHolder
import com.bring.chuchuba.adapter.base.MyItemView
import com.bring.chuchuba.adapter.base.MyItem
import android.view.LayoutInflater
import com.bring.chuchuba.R
import com.bring.chuchuba.adapter.viewholder.ViewHolderMission
import com.bring.chuchuba.adapter.viewholder.ViewHolderViewpager
import com.bring.chuchuba.databinding.ItemMissionBinding


class RecyclerViewAdapter(private val adapterType: BaseViewHolder)
    : RecyclerView.Adapter<MyItemView>() {
    private val TAG: String = "로그"
    private var listData : ArrayList<MyItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemView {
        Log.d(TAG, "RecyclerViewAdapter ~ onCreateViewHolder() called")

        return when (adapterType) {
            BaseViewHolder.MISSION -> {
                val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolderMission(binding)
            }
            BaseViewHolder.VIEWPAGER -> {
                val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolderMission(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MyItemView, position: Int) {
        when (holder) {
            is ViewHolderViewpager -> {
                val viewHolder: ViewHolderViewpager = holder
                viewHolder.onBind(listData[position])
            }
            is ViewHolderMission -> {
                val viewHolder: ViewHolderMission = holder
                viewHolder.onBind(listData[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun addItem(data: MyItem) = listData.add(data)
    fun setList(data: ArrayList<MyItem>) {
        listData = data
    }
}