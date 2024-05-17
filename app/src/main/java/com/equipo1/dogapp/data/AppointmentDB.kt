package com.equipo1.dogapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.equipo1.dogapp.model.InventoryAppointment

@Database(entities = [InventoryAppointment::class], version = 1)
abstract class AppointmentDB  : RoomDatabase() {
    abstract fun inventoryAppointment(): AppointmentInterface
    companion object{
        fun getDatabase(context: Context): AppointmentDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AppointmentDB::class.java,
                "dog_app.db"
            ).build()
        }
    }
}