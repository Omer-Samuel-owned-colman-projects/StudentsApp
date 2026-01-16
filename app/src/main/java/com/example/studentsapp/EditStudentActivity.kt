package com.example.studentsapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityEditStudentBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding
    private var studentId: String? = null
    private var currentStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getStringExtra("student_id")
        
        currentStudent = Model.instance.getStudentById(studentId ?: "")
        
        if (currentStudent != null) {
            bindData(currentStudent!!)
        } else {
            finish()
            return
        }

        binding.editStudentSaveBtn.setOnClickListener {
            saveStudent()
        }

        binding.editStudentDeleteBtn.setOnClickListener {
            deleteStudent()
        }
        
        binding.editStudentCancelBtn.setOnClickListener {
            finish()
        }
    }

    private fun bindData(student: Student) {
        binding.editStudentForm.newStudentNameEt.setText(student.name)
        binding.editStudentForm.newStudentIdEt.setText(student.idNumber)
        binding.editStudentForm.newStudentPhoneEt.setText(student.phone)
        binding.editStudentForm.newStudentAddressEt.setText(student.address)
        binding.editStudentForm.newStudentCheckedCb.isChecked = student.isChecked
    }

    private fun saveStudent() {
        val name = binding.editStudentForm.newStudentNameEt.text.toString()
        val idNumber = binding.editStudentForm.newStudentIdEt.text.toString()
        val phone = binding.editStudentForm.newStudentPhoneEt.text.toString()
        val address = binding.editStudentForm.newStudentAddressEt.text.toString()
        val isChecked = binding.editStudentForm.newStudentCheckedCb.isChecked

        if (name.isBlank() || idNumber.isBlank()) {
             Toast.makeText(this, "Name and ID are required", Toast.LENGTH_SHORT).show()
             return
        }

        val updatedStudent = Student(
            id = currentStudent!!.id, // Perform update on same ID
            name = name,
            idNumber = idNumber,
            phone = phone,
            address = address,
            isChecked = isChecked
        )
        
        Model.instance.updateStudent(updatedStudent)
        finish()
    }

    private fun deleteStudent() {
        if (currentStudent != null) {
            Model.instance.deleteStudent(currentStudent!!)
            finish()
        }
    }
}
