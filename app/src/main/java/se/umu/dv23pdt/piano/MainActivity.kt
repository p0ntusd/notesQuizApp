/**
 * A note quiz application to assist the user
 * in learning to read sheet music. There
 * are a settings activity and a stats activity
 * together with the main activity, which is the game.
 *
 * It uses the model, view, controller
 * design pattern, and this MainActivity class
 * is the view.
 *
 * @author  Pontus Dahlkvist
 * @date    18/03 -25
 */

package se.umu.dv23pdt.piano

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
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.content.Context
import android.content.DialogInterface
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView

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

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var sensorEventListener: SensorEventListener? = null
    private var shakeThreshold = 1000f
    private val shakeCooldown = 3000L
    private var lastShakeTime: Long = 0
    private var isDialogUp = false

    /** 
     * Is called when activity starts. Will initialize
     * everything and restore a previous game state
     * if one exists.
     *
     * @param savedInstanceState    Previously saved state.
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base)

        val inflater = layoutInflater
        val content = inflater.inflate(R.layout.activity_main, null)
        findViewById<FrameLayout>(R.id.contentFrame).addView(content)

        currentNoteImageView = findViewById(R.id.currentNodeImage)

        initTextViews()
        initButtons()
        initKeys()
        setKeysActionListeners()
        initNotesImages()
        initShakeListener()

        controller.addView(this)
        controller.initModel()

        loadSettings()
        loadGameState()
        controller.nextNote()

        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setupBottomNav(navView, this)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    /**
     * Initializes the shake/movement sensor and sets
     * a listener so that it detects when the phone is shook.
     *
     * Inspiration was taken from: https://stackoverflow.com/questions/2317428/how-to-refresh-app-upon-shaking-the-device
     */
    private fun initShakeListener() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorEventListener = object : SensorEventListener {
            private var lastX = 0f
            private var lastY = 0f
            private var lastZ = 0f
            private var lastUpdate: Long = 0

            override fun onSensorChanged(event: SensorEvent) {
                val currentTime = System.currentTimeMillis()

                if ((currentTime - lastUpdate) > 100) {
                    val diffTime = currentTime - lastUpdate
                    lastUpdate = currentTime

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    val speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

                    if (speed > shakeThreshold && currentTime - lastShakeTime > shakeCooldown && !isDialogUp) {
                        lastShakeTime = currentTime
                        isDialogUp = true
                        showRestartDialog()
                    }

                    lastX = x
                    lastY = y
                    lastZ = z
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
        }

        sensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    /**
     * Opens a little window where the user
     * can decide if they want to restart the
     * score counting or not. It called upon when
     * the user shakes their phones.
     */
    private fun showRestartDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Restart")
        builder.setMessage("Restart score counter?")

        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                isDialogUp = false
                restartGame()
            }
        })

        builder.setNegativeButton("No", object : DialogInterface.OnClickListener {
            override fun onClick(dialogInterface: DialogInterface?, which: Int) {
                isDialogUp = false
            }
        })

        builder.show()
    }

    /**
     * Restarts the current game, resetting
     * all the score counters to 0.
     */
    private fun restartGame() {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val editor = prefs.edit()

        editor.putInt("correctAnswers", 0)
        editor.putInt("wrongAnswers", 0)
        editor.putInt("currentStreak", 0)
        editor.apply()

        controller.setCorrectAnswers(0)
        controller.setWrongAnswers(0)
        controller.setCurrentStreak(0)
        controller.updateViewTexts()
        controller.nextNote()


        val intent = intent
        finish()
        startActivity(intent)
    }

    /**
     * Saves all the score values if one is
     * exceeded.
     *
     * @param correct   number of Correct
     * @param wrong     number of incorrect
     * @param streak    streak number
     */
    fun saveHighScores(correct: Int, wrong: Int, streak: Int) {
        val prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)
        val editor = prefs.edit()

        if (correct > prefs.getInt("highScoreCorrect", 0)) {
            editor.putInt("highScoreCorrect", correct)
        }
        if (wrong > prefs.getInt("highScoreWrong", 0)) {
            editor.putInt("highScoreWrong", wrong)
        }
        if (streak > prefs.getInt("highScoreStreak", 0)) {
            editor.putInt("highScoreStreak", streak)
        }

        editor.apply()
    }


    /**
     * Loads the settings and sets the clef mode
     * to whatever is saved.
     */
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
        statsButton.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            startActivity(intent)
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
        controller.nextNote()
    }

    /**
     * Saves the state of the game scores when
     * the activity is closed.
     */
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

    /**
     * Loads the games previously saved state
     * when this activity starts.
     */
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

    /**
     * Saves the game state when activity is paused.
     */
    override fun onPause() {
        super.onPause()
        saveGameState()

        if (sensorEventListener != null) {
            sensorManager.unregisterListener(sensorEventListener)
        }
    }

    /**
     * Updates the correct counter.
     *
     * @param   correctNumber   New correct number.
     */
    fun updateCorrectText(correctNumber: Int) {
        correctText.setText("Correct: " + correctNumber)
    }

    /**
     * Updates the incorrect counter.
     *
     * @param wrongAnswers  New incorrect number.
     */
    fun updateWrongText(wrongAnswers: Int) {
        wrongText.setText("Incorrect: " + wrongAnswers)
    }

    /**
     * Updates the streak counter.
     *
     * @param currentStreak New streak number.
     */
    fun updateStreakText(currentStreak: Int) {
        streakText.setText("Streak: " + currentStreak)
    }
}
