package com.example.studentsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.studentsapp.databinding.ActivityMainBinding
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var adapter: StudentRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val students = Model.instance.getAllStudents()

        binding.studentsRecyclerView.setHasFixedSize(true)
        binding.studentsRecyclerView.layoutManager = LinearLayoutManager(this)
        
        adapter = StudentRecyclerAdapter(students)
        adapter?.listener = object : StudentRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Log.d("TAG", "On click listener on position $position")
            }

            override fun onStudentClick(student: Student) {
                Log.d("TAG", "Clicked on student: ${student.name}")
                val intent = Intent(this@MainActivity, StudentDetailsActivity::class.java)
                intent.putExtra("student_id", student.id)
                startActivity(intent)
            }
        }
        binding.studentsRecyclerView.adapter = adapter

        binding.addStudentFab.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }
    }
    
    override fun onResume() {
        super.onResume()
        adapter?.notifyDataSetChanged()
    }
}