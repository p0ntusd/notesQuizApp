/**
 * A note quiz application to assist the user
 * in learning to read sheet music.
 *
 * It uses the model, view, controller
 * design pattern, and this MainActivity class
 * is the view.
 *
 * @author  Pontus Dahlkvist
 * @date    18/03 -25
 */

package com.example.mobilou3

/**
 * -------------------- Imports --------------------
 */
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * -------------------- Class MainActivity --------------------
 */
class MainActivity : AppCompatActivity() {

    private lateinit var settingsButton: Button
    private lateinit var statsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initButtons()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Initializes the settingsButton and
     * sets an actionlistener.
     */
    private fun initSettingsButton() {
        settingsButton = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener{
            //open settings
        }
    }

    /**
     * Initializes the statsButton and
     * sets an actionListener.
     */
    private fun initStatsButton() {
        statsButton = findViewById(R.id.statsButton)
        statsButton.setOnClickListener{
            //open stats
        }
    }

    /**
     * Initializes the stats and settings
     * buttons.
     */
    private fun initButtons() {
        initSettingsButton()
        initStatsButton()
    }

    /**
     * Saves the game state upon closing out
     * of the application.
     *
     * @param   outState    Bundle to save the games state.
     */
    @Override
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

    }

    /**
     * Restores the previous game state upon reopening of
     * the application.
     */
    @Override
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    /**
     * In case the game is ever paused. Im not
     * sure if this is even nessecary, but
     * there was some problems with controller/view
     * connection so i will keep it just in case.
     */
    @Override
    override fun onResume() {
        super.onResume()
    }


}