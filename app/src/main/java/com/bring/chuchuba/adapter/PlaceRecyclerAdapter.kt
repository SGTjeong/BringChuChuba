package com.bring.chuchuba.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.adapter.util.MissionDiffUtil
import com.bring.chuchuba.adapter.viewholder.ViewHolderMission
import com.bring.chuchuba.databinding.ItemMissionBinding
import com.bring.chuchuba.model.mission.MissionsItem

/**
 * 로직을 하나의 어댑터로 데이터 타입만 바꿔가면서 쓰고싶은게 짜고 싶은데
 */
class PlaceRecyclerAdapter : ListAdapter<MissionsItem, ViewHolderMission>(MissionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMission {
        val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderMission(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderMission, position: Int) {
        val viewHolder: ViewHolderMission = holder
        viewHolder.onBind(getItem(position))
    }
}