package com.bring.chuchuba.adapter.mission

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bring.chuchuba.databinding.ItemMissionBinding
import com.bring.chuchuba.model.mission.MissionsItem

class MissionListAdapter(val callback : (MissionsItem)->Unit) : ListAdapter<MissionsItem, ViewHolderMission>(MissionDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMission {
        val binding = ItemMissionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderMission(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderMission, position: Int) {
        val viewHolder: ViewHolderMission = holder
        viewHolder.onBind(getItem(position))
        viewHolder.itemView.setOnClickListener {
            callback(getItem(position))
        }
    }
}