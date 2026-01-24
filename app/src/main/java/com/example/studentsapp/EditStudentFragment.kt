package com.example.studentsapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.studentsapp.databinding.FragmentEditStudentBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student
import java.util.Calendar

class EditStudentFragment : Fragment() {

    private var _binding: FragmentEditStudentBinding? = null
    private val binding get() = _binding!!
    private val args: EditStudentFragmentArgs by navArgs()
    private var currentStudent: Student? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studentId = args.studentId
        currentStudent = Model.instance.getStudentById(studentId ?: "")

        if (currentStudent != null) {
            bindData(currentStudent!!)
        } else {
            findNavController().popBackStack()
            return
        }

        setupDatePicker()
        setupTimePicker()

        binding.editStudentSaveBtn.setOnClickListener {
            saveStudent()
        }

        binding.editStudentDeleteBtn.setOnClickListener {
            deleteStudent()
        }

        binding.editStudentCancelBtn.setOnClickListener {
            findNavController().popBackStack() 
        }
    }

    private fun bindData(student: Student) {
        binding.editStudentForm.newStudentNameEt.setText(student.name)
        binding.editStudentForm.newStudentIdEt.setText(student.idNumber)
        binding.editStudentForm.newStudentPhoneEt.setText(student.phone)
        binding.editStudentForm.newStudentAddressEt.setText(student.address)
        binding.editStudentForm.newStudentCheckedCb.isChecked = student.isChecked
        binding.editStudentForm.newStudentBirthDateEt.setText(student.birthDate)
        binding.editStudentForm.newStudentBirthTimeEt.setText(student.birthTime)
    }

    private fun setupDatePicker() {
        binding.editStudentForm.newStudentBirthDateTil.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    binding.editStudentForm.newStudentBirthDateEt.setText("$selectedDay/${selectedMonth + 1}/$selectedYear")
                },
                year, month, day
            )
            datePickerDialog.show()
        }
    }

    private fun setupTimePicker() {
        binding.editStudentForm.newStudentBirthTimeTil.setEndIconOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    val amPm = if (selectedHour < 12) "AM" else "PM"
                    val displayHour = if (selectedHour == 0) 12 else if (selectedHour > 12) selectedHour - 12 else selectedHour
                    val displayMinute = if (selectedMinute < 10) "0$selectedMinute" else selectedMinute
                    binding.editStudentForm.newStudentBirthTimeEt.setText("$displayHour:$displayMinute $amPm")
                },
                hour, minute, false
            )
            timePickerDialog.show()
        }
    }

    private fun saveStudent() {
        val name = binding.editStudentForm.newStudentNameEt.text.toString()
        val idNumber = binding.editStudentForm.newStudentIdEt.text.toString()
        val phone = binding.editStudentForm.newStudentPhoneEt.text.toString()
        val address = binding.editStudentForm.newStudentAddressEt.text.toString()
        val isChecked = binding.editStudentForm.newStudentCheckedCb.isChecked
        val birthDate = binding.editStudentForm.newStudentBirthDateEt.text.toString()
        val birthTime = binding.editStudentForm.newStudentBirthTimeEt.text.toString()

        if (name.isBlank() || idNumber.isBlank()) {
            Toast.makeText(context, "Name and ID are required", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedStudent = Student(
            id = currentStudent!!.id,
            name = name,
            idNumber = idNumber,
            phone = phone,
            address = address,
            isChecked = isChecked,
            birthDate = birthDate,
            birthTime = birthTime
        )

        Model.instance.updateStudent(updatedStudent)
        
        showSuccessDialog("Student updated successfully!")
    }

    private fun deleteStudent() {
        if (currentStudent != null) {
            Model.instance.deleteStudent(currentStudent!!)
            findNavController().popBackStack()
        }
    }

    private fun showSuccessDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle("Success")
            .setMessage(message)
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
