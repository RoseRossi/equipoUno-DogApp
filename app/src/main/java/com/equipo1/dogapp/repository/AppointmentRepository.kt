package com.equipo1.dogapp.repository

import android.content.Context
import com.equipo1.dogapp.data.AppointmentDB
import com.equipo1.dogapp.data.AppointmentInterface
import com.equipo1.dogapp.model.InventoryAppointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppointmentRepository(val context: Context){
    private var appointmentDB: AppointmentInterface = AppointmentDB.getDatabase(context).inventoryAppointment()
    suspend fun saveInventory(inventory:InventoryAppointment){
        withContext(Dispatchers.IO){
            appointmentDB.save(inventory)
        }
    }

    suspend fun getListInventory():MutableList<InventoryAppointment>{
        return withContext(Dispatchers.IO){
            appointmentDB.getList()
        }
    }

    suspend fun deleteInventory(inventory: InventoryAppointment){
        withContext(Dispatchers.IO){
            appointmentDB.delete(inventory)
        }
    }

    suspend fun updateRepositoy(inventory: InventoryAppointment){
        withContext(Dispatchers.IO){
            appointmentDB.update(inventory)
        }
    }
}