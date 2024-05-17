package com.equipo1.dogapp.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.equipo1.dogapp.R
import com.equipo1.dogapp.api.DogBreedsResponse
import com.equipo1.dogapp.api.RetrofitClient
import com.equipo1.dogapp.databinding.FragmentAddNewAppointmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.fragment.app.viewModels
import com.equipo1.dogapp.model.InventoryAppointment
import com.equipo1.dogapp.viewmodel.AppointmentModel

class AddNewAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentAddNewAppointmentBinding
    private lateinit var adapter: ArrayAdapter<String>
    private val breedList = mutableListOf<String>()
    private val appointmentModel : AppointmentModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddNewAppointmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButton()
        textWatcher()

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_addNewAppointmentFragment_to_appointmentSchedulerFragment)
        }

        val items = resources.getStringArray(R.array.illness_array)
        val illnessAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        illnessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = illnessAdapter

        setupAutoCompleteTextView()
        fetchDogBreeds()
    }

    private fun setupAutoCompleteTextView() {
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, breedList)
        binding.autoCompleteText.setAdapter(adapter)
        binding.autoCompleteText.threshold = 2 // Start showing suggestions after 2 characters
    }

    private fun fetchDogBreeds() {
        RetrofitClient.dogApiService.dogBreeds?.enqueue(object : Callback<DogBreedsResponse?> {
            override fun onResponse(call: Call<DogBreedsResponse?>, response: Response<DogBreedsResponse?>) {
                if (response.isSuccessful) {
                    response.body()?.message?.let { breeds ->
                        breedList.clear()
                        for ((key, value) in breeds) {
                            breedList.add(key)
                            value.forEach { subBreed ->
                                breedList.add("$key $subBreed")
                            }
                        }
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch dog breeds", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse?>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun textWatcher() {
        val listText = listOf(binding.editText1, binding.autoCompleteText, binding.editText2, binding.editText3)

        for (editText in listText) {
            editText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val isFull = listText.all {
                        it.text.toString().isNotEmpty()
                    }
                    updateButtonState(isFull)
                }
            })
        }
    }

    private fun setupButton() {
        binding.btnSave.isEnabled = false
        updateButtonState(false)

        binding.btnSave.setOnClickListener {
            val selectedSymptom = binding.spinner.selectedItem.toString()
            if (selectedSymptom.isBlank() || selectedSymptom == getString(R.string.default_spinner_text)) {
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else {
                val spinner = binding.spinner
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
                appointmentModel.save(inventory)
                findNavController().navigate(R.id.action_addNewAppointmentFragment_to_appointmentSchedulerFragment)
            }
        }
    }

    private fun updateButtonState(isEnabled: Boolean) {
        binding.btnSave.isEnabled = isEnabled
        val textColor = if (isEnabled) {
            ContextCompat.getColor(requireContext(), R.color.white)
        } else {
            ContextCompat.getColor(requireContext(), R.color.black)
        }
        binding.btnSave.setTextColor(textColor)
    }
}
