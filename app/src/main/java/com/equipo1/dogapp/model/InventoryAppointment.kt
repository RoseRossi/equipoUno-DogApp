package com.equipo1.dogapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InventoryAppointment(
  @PrimaryKey (autoGenerate = true)
  val id: Int = 0,
  val pet_name: String,
  val owner_name: String,
  val pet_breed: String,
  val tel_numbe: String,
  val pel_symptoms: String
)
