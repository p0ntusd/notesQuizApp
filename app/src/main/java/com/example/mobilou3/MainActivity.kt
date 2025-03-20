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
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mobou3.ControllerSingleton

/**
 * -------------------- Class MainActivity --------------------
 */
class MainActivity : AppCompatActivity() {

    private val controller: Controller = ControllerSingleton.controller
    private lateinit var settingsButton: Button
    private lateinit var statsButton: Button
    private lateinit var keys: ArrayList<Button>
    private lateinit var notesImages: HashMap<String, Int>
    private lateinit var currentNoteImageView: ImageView
    private lateinit var correctText: TextView
    private lateinit var wrongText: TextView
    private lateinit var streakText: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        currentNoteImageView = findViewById(R.id.currentNodeImage)
        initTextViews()
        initButtons()
        initKeys()
        setKeysActionListeners()
        initNotesImages()

        controller.addView(this)
        controller.initModel()

        loadGameState()
        loadSettings()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadSettings() {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val isTrebleClefOnly = prefs.getBoolean("trebleClefOnly", false)
        val isBassClefOnly = prefs.getBoolean("bassClefOnly", false)

        if (isTrebleClefOnly) {
            controller.setClefMode("Treble")
        } else if (isBassClefOnly) {
            controller.setClefMode("Bass")
        } else {
            controller.setClefMode("Both")
        }
    }

    /**
     * Updates the displayed note picture.
     *
     * @param key   The key to the hashmap of note pictures.
     */
    fun displayNote(key: String) {
        val image = notesImages[key]!!
        currentNoteImageView.setImageResource(image)
    }

    /**
     * Initializes the textViews.
     */
    private fun initTextViews() {
        correctText = findViewById(R.id.correctText)
        wrongText  = findViewById(R.id.incorrectText)
        streakText = findViewById(R.id.streakText)
    }

    /**
     * Adds all the images of different notes to an arraylist.
     * Starts with c2 bass image, and goes up from the with the
     * white bass keys until e4, then starts a4 at treble clef and
     * goes trough all white keys up to c6.
     */
    private fun initNotesImages() {
        notesImages = hashMapOf(
            "C2_Bass" to R.drawable.c2bass,
            "D2_Bass" to R.drawable.d2bass,
            "E2_Bass" to R.drawable.e2bass,
            "F2_Bass" to R.drawable.f2bass,
            "G2_Bass" to R.drawable.g2bass,
            "A3_Bass" to R.drawable.a3bass,
            "B3_Bass" to R.drawable.b3bass,
            "C3_Bass" to R.drawable.c3bass,
            "D3_Bass" to R.drawable.d3bass,
            "E3_Bass" to R.drawable.e3bass,
            "F3_Bass" to R.drawable.f3bass,
            "G3_Bass" to R.drawable.g3bass,
            "A4_Bass" to R.drawable.a4bass,
            "B4_Bass" to R.drawable.b4bass,
            "C4_Bass" to R.drawable.c4bass,
            "D4_Bass" to R.drawable.d4bass,
            "E4_Bass" to R.drawable.e4base,
            "A4_Treble" to R.drawable.a4trebble,
            "C4_Treble" to R.drawable.c4treble,
            "D4_Treble" to R.drawable.d4treble,
            "E4_Treble" to R.drawable.e4treble,
            "F4_Treble" to R.drawable.f4treble,
            "G4_Treble" to R.drawable.g4treble,
            "A5_Treble" to R.drawable.a5treble,
            "B5_Treble" to R.drawable.b5treble,
            "C5_Treble" to R.drawable.c5treble,
            "D5_Treble" to R.drawable.d5treble,
            "E5_Treble" to R.drawable.e5treble,
            "F5_Treble" to R.drawable.f5treble,
            "G5_Treble" to R.drawable.g5treble,
            "A6_Treble" to R.drawable.a6treble,
            "B6_Treble" to R.drawable.b6treble,
            "C6_Treble" to R.drawable.c6treble
        )
    }

    /**
     * Sets actionListeners to all the keys.
     * 'i' is the key that is pressed.
     */
    private fun setKeysActionListeners() {
        for (i in 0 until 12) {
            keys[i].setOnClickListener {
                controller.keyPressed(i)
            }
        }
    }

    /**
     * Initializes the arraylist of all keys.
     * keys[0] will contain the C-key, keys[1]
     * the C#-key, and so on, going from left to
     * right.
     */
    private fun initKeys() {
        keys = ArrayList()
        keys.add(findViewById(R.id.cButton))
        keys.add(findViewById(R.id.cSharpButton))
        keys.add(findViewById(R.id.dButton))
        keys.add(findViewById(R.id.dSharpButton))
        keys.add(findViewById(R.id.eButton))
        keys.add(findViewById(R.id.fButton))
        keys.add(findViewById(R.id.fSharpButton))
        keys.add(findViewById(R.id.gButton))
        keys.add(findViewById(R.id.gSharpButton))
        keys.add(findViewById(R.id.aButton))
        keys.add(findViewById(R.id.aSharpButton))
        keys.add(findViewById(R.id.bButton))
    }

    /**
     * Initializes the settingsButton and
     * sets an actionlistener.
     */
    private fun initSettingsButton() {
        settingsButton = findViewById(R.id.settingsButton)
        settingsButton.setOnClickListener{
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
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
        outState.putInt("correctAnswers", controller.getCorrectAnswers())
        outState.putInt("wrongAnswers", controller.getWrongAnswers())
        outState.putInt("currentStreak", controller.getCurrentStreak())
        outState.putString("currentNote", controller.getCurrentNote())
    }

    /**
     * Restores the previous game state upon reopening of
     * the application.
     */
    @Override
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        controller.setCorrectAnswers(savedInstanceState.getInt("correctAnswers"))
        controller.setWrongAnswers(savedInstanceState.getInt("wrongAnswers"))
        controller.setCurrentStreak(savedInstanceState.getInt("currentStreak"))
        val savedNote: String? = savedInstanceState.getString("currentNote")
        if (savedNote != null) {
            controller.setCurrentNote(savedNote)
        } else {
            controller.setCurrentNote("C4_Treble")
        }
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
        loadGameState()
    }

    private fun saveGameState() {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putInt("correctAnswers", controller.getCorrectAnswers())
        editor.putInt("wrongAnswers", controller.getWrongAnswers())
        editor.putInt("currentStreak", controller.getCurrentStreak())
        editor.putString("currentNote", controller.getCurrentNote())
        editor.apply()

        Log.d("GameState", "Game state saved: Correct=${controller.getCorrectAnswers()}, Wrong=${controller.getWrongAnswers()}, Streak=${controller.getCurrentStreak()}, Note=${controller.getCurrentNote()}")
    }

    private fun loadGameState() {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        controller.setCorrectAnswers(prefs.getInt("correctAnswers", 0))
        controller.setWrongAnswers(prefs.getInt("wrongAnswers", 0))
        controller.setCurrentStreak(prefs.getInt("currentStreak", 0))
        controller.updateViewTexts()

        val savedNote: String? = prefs.getString("currentNote", "C4_Treble")
        if (savedNote != null) {
            controller.setCurrentNote(savedNote)
        } else {
            controller.setCurrentNote("C4_Treble")
        }
    }

    override fun onPause() {
        super.onPause()
        saveGameState()
    }

    fun updateCorrectText(correctNumber: Int) {
        correctText.setText("Correct: " + correctNumber)
    }

    fun updateWrongText(wrongAnswers: Int) {
        wrongText.setText("Incorrect: " + wrongAnswers)
    }

    fun updateStreakText(currentStreak: Int) {
        streakText.setText("Streak: " + currentStreak)
    }
}
