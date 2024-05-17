package com.equipo1.dogapp.view.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.equipo1.dogapp.databinding.FragmentAppointmentSchedulerBinding
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.viewmodel.AppointmentModel
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.equipo1.dogapp.view.adapter.ListAppointmentAdapter

class AppointmentSchedulerFragment : Fragment() {
    private lateinit var binding: FragmentAppointmentSchedulerBinding
    private val appointmentModel : AppointmentModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = FragmentAppointmentSchedulerBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        this.controllers()
        this.observadorViewModel()
    }

    private fun controllers()
    {
        binding.addNewAppointment.setOnClickListener {
            val inventory = InventoryAppointment(pet_name = "tengo dream" , pel_symptoms = "UMMMM" , tel_numbe = "phone" , owner_name = "aaaaaa" , pet_breed = "kira")
            appointmentModel.save(inventory)
            Toast.makeText(context,"Guardado en la base de datos", Toast.LENGTH_SHORT).show()
            //findNavController().navigate(R.id.action_appointmentSchedulerFragment_to_addNewAppointmentFragment)
        }
    }

    private fun observadorViewModel(){
        observerListInventory()
        observerProgress()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observerListInventory(){

        appointmentModel.getListInventory()
        appointmentModel.listInventory.observe(viewLifecycleOwner){ listInventory ->
            val recycler = binding.recyclerview
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = ListAppointmentAdapter(listInventory)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
    private fun observerProgress(){
        appointmentModel.progresState.observe(viewLifecycleOwner){status ->
            binding.progress.isVisible = status
        }
    }
}