package com.bring.chuchuba.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bring.chuchuba.adapter.base.BaseViewHolder
import com.bring.chuchuba.adapter.base.MyItemView
import com.bring.chuchuba.adapter.base.MyItem
import android.view.LayoutInflater
import com.bring.chuchuba.adapter.mission.ViewHolderMission
import com.bring.chuchuba.databinding.ItemMissionBinding


class RecyclerViewAdapter(private val adapterType: BaseViewHolder)
    : RecyclerView.Adapter<MyItemView>() {

    private var listData : ArrayList<MyItem> = ArrayList()

    override fun getItemCount() = listData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemView {
        return when (adapterType) {
            BaseViewHolder.MISSION -> {
                val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ViewHolderMission(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MyItemView, position: Int) {
        when (holder) {
            is ViewHolderMission -> {
                val viewHolder: ViewHolderMission = holder
                viewHolder.onBind(listData[position])
            }
        }
    }

    fun addItem(data: MyItem) = listData.add(data)
    fun setList(data: ArrayList<MyItem>) { listData = data }
}