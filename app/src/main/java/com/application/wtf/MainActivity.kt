package com.application.wtf

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureNavController()
        window.decorView.apply {
            val defaultSystemUI = systemUiVisibility
            systemUiVisibility = defaultSystemUI or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    private fun configureNavController() {
        navController = findNavController(R.id.nav_host_fragment)
    }
}