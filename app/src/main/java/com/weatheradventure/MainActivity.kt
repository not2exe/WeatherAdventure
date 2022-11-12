package com.weatheradventure

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weatheradventure.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}