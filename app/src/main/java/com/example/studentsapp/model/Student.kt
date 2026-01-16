package com.example.studentsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class Student(
    @PrimaryKey
    val id: String,
    val name: String,
    val idNumber: String,
    val phone: String,
    val address: String,
    var isChecked: Boolean = false
)
