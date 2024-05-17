package com.equipo1.dogapp.view.fragment

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.equipo1.dogapp.R
import com.equipo1.dogapp.databinding.FragmentEditAppointmentBinding
import androidx.fragment.app.viewModels
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.viewmodel.AppointmentModel

class EditAppointmentFragment: Fragment()  {
    private lateinit var binding: FragmentEditAppointmentBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val breedList = mutableListOf<String>()
    private val appointmentModel : AppointmentModel by viewModels()

    private fun setupButton() {
        binding.btnEdit.isEnabled = false
        updateButtonState(false)

        binding.btnEdit.setOnClickListener {
            val selectedSymptom = binding.spinner.selectedItem.toString()
            if (selectedSymptom.isBlank() || selectedSymptom == getString(R.string.default_spinner_text)) {
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else {
                val petName = binding.editText1.text.toString()
                val pelSymptoms =spinner.selectedItem.toString()
                val telNumber = binding.editText3.text.toString()
                val ownerName = binding.editText2.text.toString()
                val petBreed = binding.autoCompleteText.text.toString()

                val inventory = InventoryAppointment(
                    pet_name = petName,
                    pel_symptoms = pelSymptoms,
                    tel_numbe = telNumber,
                    owner_name = ownerName,
                    pet_breed = petBreed
                )
                appointmentModel.update(inventory)
                findNavController().navigate(R.id.action_addNewAppointmentFragment_to_appointmentSchedulerFragment)

        }
    }

    private fun updateButtonState(isEnabled: Boolean) {
        binding.btnEdit.isEnabled = isEnabled
        val textColor = if (isEnabled) {
            ContextCompat.getColor(requireContext(), R.color.white)
        } else {
            ContextCompat.getColor(requireContext(), R.color.black)
        }
        binding.btnEdit.setTextColor(textColor)
    }
}
}