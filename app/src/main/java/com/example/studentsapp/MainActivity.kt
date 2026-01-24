package com.example.studentsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.model.Model

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Model.instance.init(this)
    }
}