package com.example.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityNewStudentBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student
import java.util.UUID

class NewStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newStudentSaveBtn.setOnClickListener {
            val name = binding.formLayout.newStudentNameEt.text.toString()
            val idNumber = binding.formLayout.newStudentIdEt.text.toString()
            val phone = binding.formLayout.newStudentPhoneEt.text.toString()
            val address = binding.formLayout.newStudentAddressEt.text.toString()
            val isChecked = binding.formLayout.newStudentCheckedCb.isChecked

            if (name.isBlank() || idNumber.isBlank()) {
                Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Using UUID for internal ID, or just reusing the ID number if preferred.
            // Requirement says "Student list presents... id".
            // Let's use a UUID for the unique ID and the input ID for the student ID number
            val student = Student(
                id = UUID.randomUUID().toString(),
                name = name,
                idNumber = idNumber,
                phone = phone,
                address = address,
                isChecked = isChecked,
                pictureUrl = "" // Static for now
            )

            Model.instance.addStudent(student)
            finish()
        }

        binding.newStudentCancelBtn.setOnClickListener {
            finish()
        }
    }
}
