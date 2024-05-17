package com.equipo1.dogapp.view.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.equipo1.dogapp.databinding.ItemAppointmentBinding
import com.equipo1.dogapp.model.InventoryAppointment

class InventoryAppointmentViewHolder (binding: ItemAppointmentBinding):RecyclerView.ViewHolder(binding.root)
{
    val bindingItem = binding

    fun setItemAppointment(inventory: InventoryAppointment)
    {
        bindingItem.petName.text = inventory.pet_name
        bindingItem.pelSymptomsText.text = inventory.pel_symptoms
        bindingItem.indexAppointent.text = '1'.toString()
    }
}