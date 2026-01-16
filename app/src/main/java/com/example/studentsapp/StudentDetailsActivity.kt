package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.databinding.ActivityStudentDetailsBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding
    private var studentId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        studentId = intent.getStringExtra("student_id")
        
        // Disable all fields
        disableForm()

        binding.studentDetailsEditBtn.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("student_id", studentId)
            startActivity(intent)
        }
        
        binding.studentDetailsBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudentData()
    }

    private fun loadStudentData() {
        val student = Model.instance.getStudentById(studentId ?: "")
        if (student != null) {
            binding.studentDetailsForm.newStudentNameEt.setText(student.name)
            binding.studentDetailsForm.newStudentIdEt.setText(student.idNumber)
            binding.studentDetailsForm.newStudentPhoneEt.setText(student.phone)
            binding.studentDetailsForm.newStudentAddressEt.setText(student.address)
            binding.studentDetailsForm.newStudentCheckedCb.isChecked = student.isChecked
        } else {
            // Student might have been deleted
            finish()
        }
    }

    private fun disableForm() {
        // Disable EditTexts
        binding.studentDetailsForm.newStudentNameEt.isEnabled = false
        binding.studentDetailsForm.newStudentIdEt.isEnabled = false
        binding.studentDetailsForm.newStudentPhoneEt.isEnabled = false
        binding.studentDetailsForm.newStudentAddressEt.isEnabled = false
        binding.studentDetailsForm.newStudentCheckedCb.isEnabled = false
        
        // Make them look "disabled" or just read-only
        binding.studentDetailsForm.newStudentNameEt.setTextColor(getColor(android.R.color.black))
        binding.studentDetailsForm.newStudentIdEt.setTextColor(getColor(android.R.color.black))
        binding.studentDetailsForm.newStudentPhoneEt.setTextColor(getColor(android.R.color.black))
        binding.studentDetailsForm.newStudentAddressEt.setTextColor(getColor(android.R.color.black))
    }
}
