package com.example.studentsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.model.Model
import com.example.studentsapp.model.Student

class StudentRecyclerAdapter(var students: List<Student>) :
    RecyclerView.Adapter<StudentRecyclerAdapter.StudentViewHolder>() {

    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onStudentClick(student: Student)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_list_row, parent, false)
        return StudentViewHolder(itemView, listener, students)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
    }

    override fun getItemCount(): Int = students.size

    inner class StudentViewHolder(
        itemView: View,
        listener: OnItemClickListener?,
        students: List<Student>
    ) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.student_row_name)
        private val idTextView: TextView = itemView.findViewById(R.id.student_row_id)
        private val checkBox: CheckBox = itemView.findViewById(R.id.student_row_check_box)

        init {
            itemView.setOnClickListener {
                listener?.onStudentClick(students[adapterPosition])
            }

            checkBox.setOnClickListener {
                val student = students[adapterPosition]
                student.isChecked = checkBox.isChecked
                Model.instance.updateStudentCheckStatus(student.id, student.isChecked)
            }
        }

        fun bind(student: Student) {
            nameTextView.text = student.name
            idTextView.text = student.idNumber
            checkBox.isChecked = student.isChecked
        }
    }
}
