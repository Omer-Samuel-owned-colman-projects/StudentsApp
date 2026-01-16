package com.example.studentsapp.model

class Model private constructor() {

    private val students: MutableList<Student> = ArrayList()

    init {
        // Dummy data
        for (i in 0..5) {
            val student = Student(
                id = i.toString(),
                name = "Student $i",
                idNumber = "123456$i",
                phone = "050-123456$i",
                address = "Address $i",
                isChecked = false,
                pictureUrl = ""
            )
            students.add(student)
        }
    }

    companion object {
        val instance: Model by lazy { Model() }
    }

    fun getAllStudents(): List<Student> {
        return students
    }

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun updateStudent(updatedStudent: Student) {
        val index = students.indexOfFirst { it.id == updatedStudent.id }
        if (index != -1) {
            students[index] = updatedStudent
        }
    }

    fun deleteStudent(student: Student) {
        val index = students.indexOfFirst { it.id == student.id }
        if (index != -1) {
            students.removeAt(index)
        }
    }

    fun getStudentById(id: String): Student? {
        return students.find { it.id == id }
    }
}
