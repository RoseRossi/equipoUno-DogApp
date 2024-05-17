package com.equipo1.dogapp.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.equipo1.dogapp.R
import com.equipo1.dogapp.databinding.FragmentEditAppointmentBinding
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.viewmodel.AppointmentModel
import com.google.android.material.textfield.TextInputEditText

class EditAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentEditAppointmentBinding
    private val appointmentModel: AppointmentModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditAppointmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
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
//        binding.editPetName.text = petName
        val editPetName: TextInputEditText = view.findViewById(R.id.editPetName)
        editPetName.setText(petName)
        val editOwner: TextInputEditText = view.findViewById(R.id.editOwner)
        editOwner.setText(owner)
        val editPhone: TextInputEditText = view.findViewById(R.id.editPhone)
        editPhone.setText(phone)
        val autoCompleteTextView: AutoCompleteTextView = view.findViewById(R.id.autoCompleteText)
        autoCompleteTextView.setText(petBreed)
//        binding.autoCompleteText.text = petBreed
        binding.idNumber.text = idNumber
        if (idNumber != null) {
            if (petDetails != null) {
                controller(idNumber.toInt(), petDetails, view)
            }
        }
    }
    @SuppressLint("CutPasteId")
    fun controller(id: Int, details: String, view: View){
        val editPetNameOld: TextInputEditText = view.findViewById(R.id.editPetName)
        val editOwnerOld: TextInputEditText = view.findViewById(R.id.editOwner)
        val editPhoneOld: TextInputEditText = view.findViewById(R.id.editPhone)
        val autoCompleteTextViewOld: AutoCompleteTextView = view.findViewById(R.id.autoCompleteText)
        val petNameTextOld: String = editPetNameOld.text.toString()
        val ownerTextOld: String = editOwnerOld.text.toString()
        val phoneTextOld: String = editPhoneOld.text.toString()
        val petBreedTextOld: String = autoCompleteTextViewOld.text.toString()

        binding.backButton.setOnClickListener{
            val bundle = Bundle().apply {
                putString("petName", petNameTextOld)
                putString("petBreed", petBreedTextOld)
                putString("petDetails", details)
                putString("owner", ownerTextOld)
                putString("phone", phoneTextOld)
                putString("idNumber", id.toString())
            }
            findNavController().navigate(R.id.action_editAppointmentFragment_to_appointmentDetailsFragment, bundle)
        }
        binding.btnEdit.setOnClickListener{
            val editPetName: TextInputEditText = view.findViewById(R.id.editPetName)
            val editOwner: TextInputEditText = view.findViewById(R.id.editOwner)
            val editPhone: TextInputEditText = view.findViewById(R.id.editPhone)
            val autoCompleteTextView: AutoCompleteTextView = view.findViewById(R.id.autoCompleteText)
            val petNameText: String = editPetName.text.toString()
            val ownerText: String = editOwner.text.toString()
            val phoneText: String = editPhone.text.toString()
            val petBreedText: String = autoCompleteTextView.text.toString()
            val bundle = Bundle().apply {
                putString("petName", petNameText)
                putString("petBreed", petBreedText)
                putString("petDetails", details)
                putString("owner", ownerText)
                putString("phone", phoneText)
                putString("idNumber", id.toString())
            }
            val inventory = InventoryAppointment(
                id = id,
                pet_name = petNameText,
                pel_symptoms = details,
                tel_numbe = phoneText,
                owner_name = ownerText,
                pet_breed = petBreedText,
            )
            appointmentModel.update(inventory)
            findNavController().navigate(R.id.action_editAppointmentFragment_to_appointmentDetailsFragment, bundle)
        }
    }

    fun saveChanges(){
//        val id = binding.idNumber.text.toString().toInt()
//        val inventory = InventoryAppointment(
//            id = id,
//            pet_name = binding.editPetName.text.toString(),
//            pel_symptoms = binding.petDetails.text.toString(),
//            tel_numbe = binding.editPhone.text.toString(),
//            owner_name = binding.editOwner.text.toString(),
//            pet_breed = binding.petBreed.text.toString(),
//        )
//        appointmentModel.delete(inventory)
    }

    fun selectdata(){

    }
}