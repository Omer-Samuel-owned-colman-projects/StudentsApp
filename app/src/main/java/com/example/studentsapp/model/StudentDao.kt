package com.example.studentsapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
    @Query("SELECT * FROM students")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM students WHERE id = :id")
    fun getStudentById(id: String): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(student: Student)

    @Update
    fun update(student: Student)
    
    @Delete
    fun delete(student: Student)
}
