package com.bring.chuchuba.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bring.chuchuba.adapter.base.BaseViewHolder
import com.bring.chuchuba.adapter.base.MyItemView
import com.bring.chuchuba.adapter.base.MyItem
import android.view.LayoutInflater
import android.view.View
import com.bring.chuchuba.R
import com.bring.chuchuba.adapter.base.MyList
import com.bring.chuchuba.adapter.viewholder.ViewHolderMission
import com.bring.chuchuba.adapter.viewholder.ViewHolderViewpager


class RecyclerViewAdapter(private val adapterType: BaseViewHolder
                          ) : RecyclerView.Adapter<MyItemView>() {

    private var listData : ArrayList<MyItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyItemView {
        val view: View
        return when (adapterType) {
            BaseViewHolder.VIEWPAGER -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_mission, parent, false)
                ViewHolderViewpager(view)
            }
            BaseViewHolder.MISSION -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.item_mission, parent, false)
                ViewHolderMission(view)
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
    fun setList(data: MyList) {
        listData = data
    }
}