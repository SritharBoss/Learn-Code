package com.example.restartusingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val name: String = "Adam"
        Log.d("HelloMsg",(0 until 10)::class.simpleName.toString())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}