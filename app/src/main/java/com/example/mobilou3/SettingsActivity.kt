/**
 * This class is the settings-screen.
 * There are 2 settings to choose from,
 * Treble clef only -setting which will make
 * it so that only treble clef notes are shown,
 * and bass clef only -setting which does the same
 * for bass notes.
 *
 * @author  Pontus Dahlkvist
 * @date    21/03 -25
 */

package com.example.mobilou3

/**
 * --------------------- Imports ---------------------
 */
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

/**
 * --------------------- Class SettingsActivity ---------------------
 */
class SettingsActivity : AppCompatActivity() {

    private lateinit var backButton: Button
    private lateinit var trebleClefOnlyButton: ImageButton
    private lateinit var bassClefOnlyButton: ImageButton
    private var isTrebleClefOnly = false
    private var isBassClefOnly = false
    private lateinit var prefs: SharedPreferences

    /**
     * When activity is created. Will initialize everything
     * and load the layout.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)

        initBackButton()
        initTrebleClefOnlyButton()
        initBassClefOnlyButton()
    }

    /**
     * Initializes bass clef only button and sets it to false.
     * Adds actionlistener to notice when the button is clicked.
     * Will disable trebleClefOnlyButton when enabled.
     */
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

    /**
     * Initializes bass clef only button and sets it to false.
     * Adds actionlistener to notice when the button is clicked.
     * Will disable bassClefOnlyButton when enabled.
     */
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

    /**
     * Updates the buttons looks so they
     * match if they are selected or not.
     * Also saves both buttons states to
     * sharedPreferences.
     */
    private fun updateButtonStates() {
        trebleClefOnlyButton.isSelected = isTrebleClefOnly
        bassClefOnlyButton.isSelected = isBassClefOnly

        val editor = prefs.edit()
        editor.putBoolean("trebleClefOnly", isTrebleClefOnly)
        editor.putBoolean("bassClefOnly", isBassClefOnly)
        editor.apply()
    }

    /**
     * Initializes back button and adds the
     * actionlistener. Tackes the user back
     * to the mainActivity.
     */
    private fun initBackButton() {
        backButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}