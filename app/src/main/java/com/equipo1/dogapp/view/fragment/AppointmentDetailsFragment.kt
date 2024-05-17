// AppointmentDetailsFragment.kt
package com.equipo1.dogapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.equipo1.dogapp.R
import com.equipo1.dogapp.databinding.FragmentAppointmentDetailsBinding
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.viewmodel.AppointmentModel

class AppointmentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentDetailsBinding
    private val appointmentModel : AppointmentModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Recupera los datos del bundle
        val petName = arguments?.getString("petName")
        val petBreed = arguments?.getString("petBreed")
        val petDetails = arguments?.getString("petDetails")
        val owner = arguments?.getString("owner")
        val phone = arguments?.getString("phone")
        val idNumber = arguments?.getString("idNumber")

        // Asigna los datos a los TextView correspondientes
        binding.petName.text = petName
        binding.petBreed.text = petBreed
        binding.petDetails.text = petDetails
        binding.owner.text = owner
        binding.phone.text = phone
        binding.idNumber.text = idNumber

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_appointmentDetailsFragment_to_appointmentSchedulerFragment)
        }

        binding.btnEdit.setOnClickListener {
                val id = binding.idNumber.text.toString().toIntOrNull() ?: -1
                if (id != -1){
                navigateToAppointmentEdit(InventoryAppointment(
                id = id,
                pet_name = binding.petName.text.toString(),
                pel_symptoms = binding.petDetails.text.toString(),
                tel_numbe = binding.phone.text.toString(),
                owner_name = binding.owner.text.toString(),
                pet_breed = binding.petBreed.text.toString(),
            ))
                }
        }
        binding.btnDelete.setOnClickListener {
            val id = binding.idNumber.text.toString().toInt()
            val inventory = InventoryAppointment(
                id = id,
                pet_name = binding.petName.text.toString(),
                pel_symptoms = binding.petDetails.text.toString(),
                tel_numbe = binding.phone.text.toString(),
                owner_name = binding.owner.text.toString(),
                pet_breed = binding.petBreed.text.toString(),
            )
            appointmentModel.delete(inventory)
            findNavController().navigate(R.id.action_appointmentDetailsFragment_to_appointmentSchedulerFragment)
        }
    }

     private fun navigateToAppointmentEdit(inventoryAppointment: InventoryAppointment) {
        val bundle = Bundle().apply {
            putString("petName", inventoryAppointment.pet_name)
            putString("petBreed", inventoryAppointment.pet_breed)
            putString("petDetails", inventoryAppointment.pel_symptoms)
            putString("owner", inventoryAppointment.owner_name)
            putString("phone", inventoryAppointment.tel_numbe)
            putString("idNumber", inventoryAppointment.id.toString())
        }
        findNavController().navigate(R.id.action_appointmentDetailsFragment_to_editAppointmentFragment, bundle)
    }

}
