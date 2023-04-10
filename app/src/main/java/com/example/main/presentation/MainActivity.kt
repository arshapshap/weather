package com.example.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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