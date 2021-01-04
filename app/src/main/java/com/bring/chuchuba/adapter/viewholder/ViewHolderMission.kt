package com.bring.chuchuba.adapter.viewholder

import android.view.View
import com.bring.chuchuba.R
import com.bring.chuchuba.adapter.base.MyItem
import com.bring.chuchuba.adapter.base.MyItemView
import com.bring.chuchuba.model.mission.Mission
import com.bring.chuchuba.model.mission.MissionsItem
import kotlinx.android.synthetic.main.item_mission.view.*

/**
 * 미션 뷰홀더. 레이아웃과 연동
 */
class ViewHolderMission(itemView: View) : MyItemView(itemView) {

    private val name = itemView.name
    private val orderer = itemView.orderer
    fun onBind(item : MyItem){
        val mission = item as MissionsItem
        name.text = mission.title
        orderer.text = mission.description
        mission.client
    }
}