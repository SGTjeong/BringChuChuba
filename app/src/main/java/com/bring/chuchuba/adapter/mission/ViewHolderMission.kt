package com.bring.chuchuba.adapter.mission

import android.graphics.Movie
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.adapter.base.MyItemView
import com.bring.chuchuba.databinding.ItemMissionBinding
import com.bring.chuchuba.model.mission.MissionsItem

/**
 * 미션 뷰홀더. 레이아웃과 연동
 */
class ViewHolderMission(val binding: ItemMissionBinding) : MyItemView(binding) {
    fun onBind(item : MyItem){
        val mission = item as MissionsItem
        binding.mission = mission
    }
}