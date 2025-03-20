package com.example.mobilou3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var trebleClefOnlyButton: ImageButton
    private lateinit var bassClefOnlyButton: ImageButton
    private var isTrebleClefOnly = false
    private var isBassClefOnly = false
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)

        initBackButton()
        initTrebleClefOnlyButton()
        initBassClefOnlyButton()
    }

    private fun initBassClefOnlyButton() {
        bassClefOnlyButton = findViewById(R.id.bassClefOnlyButton)

        isTrebleClefOnly = prefs.getBoolean("trebleClefOnly", false)
        isBassClefOnly = prefs.getBoolean("bassClefOnly", false)

        trebleClefOnlyButton.isSelected = isTrebleClefOnly
        bassClefOnlyButton.isSelected = isBassClefOnly

        bassClefOnlyButton.setOnClickListener {
            if (!isBassClefOnly) {
                isBassClefOnly = true
                isTrebleClefOnly = false
            } else {
                isBassClefOnly = false
            }

            updateButtonStates()
        }
    }

    private fun initTrebleClefOnlyButton() {
        trebleClefOnlyButton = findViewById(R.id.trebleClefOnlyButton)
        bassClefOnlyButton = findViewById(R.id.bassClefOnlyButton)

        isTrebleClefOnly = prefs.getBoolean("trebleClefOnly", false)
        isBassClefOnly = prefs.getBoolean("bassClefOnly", false)

        trebleClefOnlyButton.isSelected = isTrebleClefOnly
        bassClefOnlyButton.isSelected = isBassClefOnly

        trebleClefOnlyButton.setOnClickListener {
            if (!isTrebleClefOnly) {
                isTrebleClefOnly = true
                isBassClefOnly = false
            } else {
                isTrebleClefOnly = false
            }

            updateButtonStates()
        }
    }

    private fun updateButtonStates() {
        trebleClefOnlyButton.isSelected = isTrebleClefOnly
        bassClefOnlyButton.isSelected = isBassClefOnly

        val editor = prefs.edit()
        editor.putBoolean("trebleClefOnly", isTrebleClefOnly)
        editor.putBoolean("bassClefOnly", isBassClefOnly)
        editor.apply()
    }


    private fun initBackButton() {
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}