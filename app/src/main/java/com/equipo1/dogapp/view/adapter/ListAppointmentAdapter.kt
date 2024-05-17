package com.equipo1.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.equipo1.dogapp.databinding.ItemAppointmentBinding
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.view.viewholder.InventoryAppointmentViewHolder

class ListAppointmentAdapter (private val listAppointment:MutableList<InventoryAppointment>):RecyclerView.Adapter<InventoryAppointmentViewHolder>()
{
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InventoryAppointmentViewHolder {
        val binding = ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return InventoryAppointmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InventoryAppointmentViewHolder, position: Int) {
        val inventory = listAppointment[position]
        holder.setItemAppointment(inventory)
    }

    override fun getItemCount(): Int {
        return listAppointment.size
    }

}