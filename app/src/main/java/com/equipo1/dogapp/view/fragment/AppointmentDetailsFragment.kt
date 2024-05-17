// AppointmentDetailsFragment.kt
package com.equipo1.dogapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.equipo1.dogapp.R
import com.equipo1.dogapp.databinding.FragmentAppointmentDetailsBinding

class AppointmentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentDetailsBinding

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
    }
}
