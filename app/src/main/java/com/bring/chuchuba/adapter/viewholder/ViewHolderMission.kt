package com.bring.chuchuba.adapter.viewholder

import android.graphics.Movie
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.adapter.base.MyItemView
import com.bring.chuchuba.databinding.ItemMissionBinding
import com.bring.chuchuba.model.mission.MissionsItem

/**
 * 미션 뷰홀더. 레이아웃과 연동
 */
class ViewHolderMission(itemView: ItemMissionBinding) : MyItemView(itemView) {
    var binding: ItemMissionBinding = itemView

    fun onBind(item : MyItem){
        val mission = item as MissionsItem
        binding.name.text = mission.title
        binding.orderer.text = mission.description
    }
}