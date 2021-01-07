package com.bring.chuchuba.adapter.util

import com.bring.chuchuba.adapter.base.BaseDiffUtil
import com.bring.chuchuba.model.mission.MissionsItem

/**
 * 데이터 타입마다 DiffUtil을 만들면 굳이 basediffutil을 안써도 된다.
 */
object MissionDiffUtil : BaseDiffUtil<MissionsItem>() {
    override fun areItemsTheSame(oldItem: MissionsItem, newItem: MissionsItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MissionsItem, newItem: MissionsItem): Boolean =
        oldItem == newItem
}