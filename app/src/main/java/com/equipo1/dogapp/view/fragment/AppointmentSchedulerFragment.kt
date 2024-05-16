package com.equipo1.dogapp.view.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.equipo1.dogapp.databinding.FragmentAppointmentSchedulerBinding

class AppointmentSchedulerFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentSchedulerBinding
    //private val inventoryViewModel: InventoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAppointmentSchedulerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.controllers()
    }

    private fun controllers()
    {
        binding.addNewAppointment.setOnClickListener {
            Toast.makeText(context, "UMMM YA", Toast.LENGTH_SHORT).show()
        }
    }
}