package com.example.studentsapp.model

data class Student(
    val id: String,
    val name: String,
    val idNumber: String,
    val phone: String,
    val address: String,
    var isChecked: Boolean = false,
    val pictureUrl: String = "" // Static picture used for all students for now
)
