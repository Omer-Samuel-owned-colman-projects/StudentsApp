package com.example.studentsapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.studentsapp.databinding.FragmentNewStudentBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student
import java.util.Calendar
import java.util.UUID

class NewStudentFragment : Fragment() {

    private var _binding: FragmentNewStudentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDatePicker()

        binding.newStudentSaveBtn.setOnClickListener {
            saveStudent()
        }

        binding.newStudentCancelBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupDatePicker() {
        binding.formLayout.newStudentBirthDateEt.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.formLayout.newStudentBirthDateEt.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun saveStudent() {
        val name = binding.formLayout.newStudentNameEt.text.toString()
        val idNumber = binding.formLayout.newStudentIdEt.text.toString()
        val phone = binding.formLayout.newStudentPhoneEt.text.toString()
        val address = binding.formLayout.newStudentAddressEt.text.toString()
        val isChecked = binding.formLayout.newStudentCheckedCb.isChecked
        val birthDate = binding.formLayout.newStudentBirthDateEt.text.toString()

        if (name.isBlank() || idNumber.isBlank()) {
            Toast.makeText(context, "Name and ID are required", Toast.LENGTH_SHORT).show()
            return
        }

        val student = Student(
            id = UUID.randomUUID().toString(),
            name = name,
            idNumber = idNumber,
            phone = phone,
            address = address,
            isChecked = isChecked,
            birthDate = birthDate
        )

        Model.instance.addStudent(student)
        
        showSuccessDialog()
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Success")
            .setMessage("Student saved successfully!")
            .setPositiveButton("OK") { _, _ ->
                findNavController().popBackStack()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
