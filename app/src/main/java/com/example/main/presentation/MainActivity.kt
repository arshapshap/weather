package com.example.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.main.R
import com.example.main.data.db.DatabaseHandler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        applicationContext?.let {
            DatabaseHandler.dbInitialize(it)
        }
    }
}