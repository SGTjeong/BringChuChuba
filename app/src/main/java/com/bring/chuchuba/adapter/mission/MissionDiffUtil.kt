package com.bring.chuchuba.adapter.mission

import com.bring.chuchuba.adapter.base.BaseDiffUtil
import com.bring.chuchuba.model.mission.MissionsItem

object MissionDiffUtil : BaseDiffUtil<MissionsItem>() {
    override fun areItemsTheSame(oldItem: MissionsItem, newItem: MissionsItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MissionsItem, newItem: MissionsItem): Boolean =
        oldItem == newItem
}