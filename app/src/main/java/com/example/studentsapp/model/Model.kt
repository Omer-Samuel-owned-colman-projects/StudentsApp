package com.example.studentsapp.model

import android.content.Context

class Model private constructor() {

    companion object {
        val instance: Model by lazy { Model() }
    }

    fun init(context: Context) {
        AppLocalDb.init(context)
    }

    fun getAllStudents(): List<Student> {
        return AppLocalDb.db?.studentDao()?.getAllStudents() ?: emptyList()
    }

    fun addStudent(student: Student) {
        AppLocalDb.db?.studentDao()?.insert(student)
    }

    fun updateStudent(student: Student) {
        AppLocalDb.db?.studentDao()?.update(student)
    }

    fun deleteStudent(student: Student) {
        AppLocalDb.db?.studentDao()?.delete(student)
    }

    fun getStudentById(id: String): Student? {
        return AppLocalDb.db?.studentDao()?.getStudentById(id)
    }

    fun updateStudentCheckStatus(id: String, isChecked: Boolean) {
        val student = getStudentById(id)
        student?.let {
            it.isChecked = isChecked
            updateStudent(it)
        }
    }
}
