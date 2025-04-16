/**
 *  This is the model class for the
 *  note quiz application. This class handles
 *  most of the logic and heavy operations.
 *
 *  @author Pontus Dahlkvist
 *  @date 21/03 -25
 */

package se.umu.dv23pdt.piano

/**
 * ---------------- Imports ----------------
 */
import android.util.Log

/**
 * ----------------Class Model ----------------
 */
class Model(private var controller: Controller) {

    private lateinit var notesImages: HashMap<String, Int>
    private var currentNote: String = "C4_Treble"
    private var correctAnswers: Int = 0
    private var wrongAnswers: Int = 0
    private var currentStreak: Int = 0
    private var clefMode: String = "Both"

    init {
        initNotesImages()
    }

    /**
     * Returns current streak.
     *
     * @return  Current streak.
     */
    fun getCurrentStreak(): Int {
        return currentStreak
    }

    /**
     * Sets the ClefMode-setting.
     *
     * @param mode  The new clef mode.
     */
    fun setClefMode(mode: String) {
        clefMode = mode
    }

    /**
     * Returns a random note name. If Treble mode
     * is active only treble notes will be returned.
     * If bass mode it active only bass notes will be
     * returned. If no mode is active both types of
     * notes will be returned.
     *
     * Also sets whatever note is decided to the current note.
     *
     * @return  The name of the note that is decided.
     */
    fun getRandomNoteImage(): String {
        val filteredNotes = when (clefMode) {
            "Treble" -> notesImages.keys.filter { it.contains("Treble") }
            "Bass" -> notesImages.keys.filter { it.contains("Bass") }
            else -> notesImages.keys.toList()
        }
        currentNote = filteredNotes.random()
        return currentNote
    }

    /**
     * Returns current number of correct answers.
     *
     * @return  Number of current correct answers.
     */
    fun getCorrectAnswers(): Int {
        return correctAnswers
    }

    /**
     * Sets number of current correct answers.
     *
     * @param value New number.
     */
    fun setCorrectAnswers(value: Int) {
        Log.d("GameState", "setCorrectAnswers() called with value: $value")
        correctAnswers = value
    }

    /**
     * Returns current number of incorrect answers.
     *
     * @return  Number of incorrect answers.
     */
    fun getWrongAnswers():Int {
        return wrongAnswers
    }

    /**
     * Sets number of incorrect answers.
     *
     * @return  New number.
     */
    fun setWrongAnswers(value: Int) {
        wrongAnswers = value
    }

    /**
     * Returns the current note.
     *
     * @return  The current note.
     */
    fun getCurrentNote(): String {
        return currentNote
    }

    /**
     * Sets the current note.
     *
     * @param note  The name of the new note to be set.
     */
    fun setCurrentNote(note: String) {
        currentNote = note
    }

    /**
     * Is called when the user has pushed a key.
     * Depending on if the answer was correct or not,
     * the corresponding method is called in controller
     * and the score counters are adjusted.
     *
     * @param i The number for the key that is pressed.
     */
    fun keyPressed(i: Int) {
        if (i == translateNoteToKey(currentNote)) {
            correctAnswers++
            currentStreak++
            controller.answeredCorrect(correctAnswers)
        } else {
            wrongAnswers++
            currentStreak = 0
            controller.answeredWrong(wrongAnswers)
        }
    }

    /**
     * At first I decided to save each key as a number,
     * so here I need to translate each note into its
     * corresponding key number. For example, key 0 is all the
     * C-keys, and 2 is all the D keys.
     *
     * Some numbers are skipped, because in this version of the program
     * the black keys are not yet implemented to work. There are no images
     * to show for them because I forgot to make the sharps.
     *
     * @param note  The note to be translated.
     *
     * @return      The key number of the note. -1 if failed.
     */
    fun translateNoteToKey(note: String): Int {
        if (note == "C2_Bass" || note == "C3_Bass" || note == "C4_Bass" ||
            note == "C4_Treble" || note == "C5_Treble" || note == "C6_Treble") {
            return 0
        }
        if (note == "D2_Bass" || note == "D3_Bass" || note == "D4_Bass" ||
            note == "D4_Treble" || note == "D5_Treble") {
            return 2
        }
        if (note == "E2_Bass" || note == "E3_Bass" || note == "E4_Bass" ||
            note == "E4_Treble" || note == "E5_Treble") {
            return 4
        }
        if (note == "F2_Bass" || note == "F3_Bass" || note == "F4_Bass" ||
            note == "F4_Treble" || note == "F5_Treble") {
            return 5
        }
        if (note == "G2_Bass" || note == "G3_Bass" || note == "G4_Bass" ||
            note == "G4_Treble" || note == "G5_Treble") {
            return 7
        }
        if (note == "A3_Bass" || note == "A4_Bass" ||
            note == "A4_Treble" || note == "A5_Treble" || note == "A6_Treble") {
            return 9
        }
        if (note == "B3_Bass" || note == "B4_Bass" ||
            note == "B5_Treble" || note == "B6_Treble") {
            return 11
        }

        return -1
    }

    /**
     * Initializes notesImages. It holds all
     * the images of notes.
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
     * Sets the current streak.
     *
     * @param value The number.
     */
    fun setCurrentStreak(value: Int) {
        currentStreak = value
    }
}