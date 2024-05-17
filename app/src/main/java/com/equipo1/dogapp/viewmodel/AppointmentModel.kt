package com.equipo1.dogapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.repository.AppointmentRepository
import kotlinx.coroutines.launch

class AppointmentModel(application: Application) : AndroidViewModel(application)
{
    val context = getApplication<Application>()
    private val inventoryRepository = AppointmentRepository(context)


    private val _listInventory = MutableLiveData<MutableList<InventoryAppointment>>()
    val listInventory: LiveData<MutableList<InventoryAppointment>> get() = _listInventory

    private val _progresState = MutableLiveData(false)
    val progresState: LiveData<Boolean> = _progresState

    fun  save(inventory: InventoryAppointment)
    {
        viewModelScope.launch {

            _progresState.value = true
            try {
                inventoryRepository.saveInventory(inventory)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

    fun getListInventory() {
        viewModelScope.launch {
            _progresState.value = true
            try {
                _listInventory.value = inventoryRepository.getListInventory()
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }

        }
    }
}