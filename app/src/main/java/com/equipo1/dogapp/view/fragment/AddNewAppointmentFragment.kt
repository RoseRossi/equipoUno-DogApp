package com.equipo1.dogapp.view.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.equipo1.dogapp.databinding.FragmentAddNewAppointmentBinding
import android.widget.ArrayAdapter
import android.widget.Toast
import com.equipo1.dogapp.R
import androidx.core.content.ContextCompat

class AddNewAppointmentFragment : Fragment() {
    private lateinit var binding: FragmentAddNewAppointmentBinding

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

        val items = resources.getStringArray(R.array.illness_array).toMutableList()
        items.add(0, getString(R.string.default_spinner_text))
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.setSelection(0)
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
                Toast.makeText(requireContext(), "Cita guardada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateButtonState(isEnabled: Boolean) {
        binding.btnSave.isEnabled = isEnabled
        val textColor = if (isEnabled) {
            ContextCompat.getColor(requireContext(), R.color.button_enabled_text_color)
        } else {
            ContextCompat.getColor(requireContext(), R.color.button_disabled_text_color)
        }
        binding.btnSave.setTextColor(textColor)
    }
}
