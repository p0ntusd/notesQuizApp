/**
 * This is the stats-screen for the piano sheet
 * music note quiz application. Is shows
 * the top of all time correct answers,
 * incorrect answers and longest streak.
 *
 * There is room for one more stat to be tracked,
 * but I haven't yet decided on what to pick.
 *
 * @author  Pontus Dahlkvist
 * @date    21/03 -25
 */

package se.umu.dv23pdt.piano

/**
 * ------------------------ Imports ------------------------
 */
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * ------------------------ Class StatsActivity ------------------------
 */
class StatsActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var highScoreCorrectText: TextView
    private lateinit var highScoreWrongText: TextView
    private lateinit var highScoreStreakText: TextView
    private lateinit var backButton: Button

    /**
     * Is called when activity is created.
     * Displays every stat and sets the
     * back-buttons actionlistener.
     *
     * @param savedInstanceState    Previously saved state.
     */
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        setupBottomNav(navView, this)

        val inflater = layoutInflater
        val content = inflater.inflate(R.layout.activity_stats, null)
        findViewById<FrameLayout>(R.id.contentFrame).addView(content)

        prefs = getSharedPreferences("GamePrefs", MODE_PRIVATE)

        highScoreCorrectText = findViewById(R.id.highScoreCorrectText)
        highScoreWrongText = findViewById(R.id.highScoreWrongText)
        highScoreStreakText = findViewById(R.id.highScoreStreakText)
        backButton = findViewById(R.id.backButton)

        loadHighScores()

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Loads all top scores and displays them.
     */
    private fun loadHighScores() {
        val highScoreCorrect = prefs.getInt("highScoreCorrect", 0)
        val highScoreWrong = prefs.getInt("highScoreWrong", 0)
        val highScoreStreak = prefs.getInt("highScoreStreak", 0)

        highScoreCorrectText.text = "Highest correct: $highScoreCorrect"
        highScoreWrongText.text = "Highest incorrect: $highScoreWrong"
        highScoreStreakText.text = "Longest streak: $highScoreStreak"
    }
}
