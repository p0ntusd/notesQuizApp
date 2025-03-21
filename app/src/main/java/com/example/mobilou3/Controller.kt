/**
 * This is the controller module for the
 * sheet music quiz application. It acts
 * as a middle man between the view and the model.
 *
 * @author  Pontus Dahlkvist
 * @date    19/03 -25
 */

package com.example.mobilou3

/**
 * -------------------- Imports --------------------
 */
import android.widget.Button

/**
 * -------------------- Class Controller --------------------
 */
class Controller {

    private lateinit var keys: ArrayList<Button>
    private lateinit var view: MainActivity
    private lateinit var model: Model

    /**
     * Notices when a key is pressed.
     * 'i' is the key that is pressed,
     * in order from left to right.
     * 0 = C, 1 = C#, 2 = D and so on.
     */
    fun keyPressed(i: Int) {
        model.keyPressed(i)


    }

    /**
     * Is called after the user has made a guess.
     * Will change the displayed note into a new one
     * and let the user guess again.
     */
    fun nextNote() {
        view.displayNote(model.getRandomNoteImage())
    }

    /**
     * Adds the view to the controller
     * to allow for communication.
     *
     * @param mainActivity  The view.
     */
    fun addView(mainActivity: MainActivity) {
        this.view = mainActivity
    }

    /**
     * Initializes the model. Adds this controller
     * to that model.
     */
    fun initModel() {
        model = Model(this)
    }

    /**
     * Is called when the user makes a correct guess.
     * Updates the views correct number.
     *
     * @param correctNumber The number of correct answers.
     */
    fun answeredCorrect(correctNumber: Int) {
        view.updateCorrectText(correctNumber)
        view.updateStreakText(model.getCurrentStreak())
        nextNote()
        view.saveHighScores(correctNumber, model.getWrongAnswers(), model.getCurrentStreak())
    }

    /**
     * Is called when the user makes an incorrect guess.
     * Updates the views incorrect number.
     *
     * @param wrongAnswers  The number of incorrect guesses.
     */
    fun answeredWrong(wrongAnswers: Int) {
        view.updateWrongText(wrongAnswers)
        view.updateStreakText(model.getCurrentStreak())
        nextNote()
        view.saveHighScores(model.getCorrectAnswers(), wrongAnswers, model.getCurrentStreak())
    }

    /**
     * Updates all the score counters
     * to the models current values.
     */
    fun updateViewTexts() {
        view.updateStreakText(model.getCurrentStreak())
        view.updateWrongText(model.getWrongAnswers())
        view.updateCorrectText(model.getCorrectAnswers())
    }

    /**
     * Changes the current "clef mode" to be
     * what the user has clicked. Can be either
     * Bass mode or Treble mode or both, but not
     * neither.
     *
     * @param mode  The clef mode to be set.
     */
    fun setClefMode(mode: String) {
        model.setClefMode(mode)
        println("setClefMode")
        view.displayNote(model.getRandomNoteImage())
    }

    /**
     * Returns the current amount of correct answers.
     *
     * @return  Number of correct answers.
     */
    fun getCorrectAnswers(): Int {
        return model.getCorrectAnswers()
    }

    /**
     * Sets the number of correct answers
     * to the given value.
     *
     * @param value    The new value.
     */
    fun setCorrectAnswers(value: Int) {
        model.setCorrectAnswers(value)
    }

    /**
     * Returns the number of incorrect answers.
     *
     * @return  amount of incorrect answers.
     */
    fun getWrongAnswers(): Int {
        return model.getWrongAnswers()
    }

    /**
     * Sets the number of incorrect answers
     * to the given value.
     *
     * @param value    The new value.
     */
    fun setWrongAnswers(value: Int) {
        model.setWrongAnswers(value)
    }

    /**
     * Returns the current streak the user is on.
     *
     * @return  current streak value.
     */
    fun getCurrentStreak(): Int {
        return model.getCurrentStreak()
    }

    /**
     * Sets the current streak to the given value.
     *
     * @param value    The new streak value.
     */
    fun setCurrentStreak(value: Int) {
        model.setCurrentStreak(value)
    }

    /**
     * Returns the current note that is active.
     *
     * @return  the current note as a string.
     */
    fun getCurrentNote(): String {
        return model.getCurrentNote()
    }

    /**
     * Sets the current note to the given one
     * and updates the view to show it.
     *
     * @param note   The note to be shown.
     */
    fun setCurrentNote(note: String) {
        model.setCurrentNote(note)
        view.displayNote(model.getCurrentNote())
    }
}

