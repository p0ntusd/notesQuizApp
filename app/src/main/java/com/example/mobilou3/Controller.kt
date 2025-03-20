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
    }

    fun getCorrectAnswers(): Int {
        return model.getCorrectAnswers()
    }

    fun setCorrectAnswers(value: Int) {
        model.setCorrectAnswers(value)
    }

    fun getWrongAnswers(): Int {
        return model.getWrongAnswers()
    }
    fun setWrongAnswers(value: Int) {
        model.setWrongAnswers(value)
    }

    fun getCurrentStreak(): Int {
        return model.getCurrentStreak()
    }
    fun setCurrentStreak(value: Int) {
        model.setCurrentStreak(value)
    }

    fun getCurrentNote(): String {
        return model.getCurrentNote()
    }
    fun setCurrentNote(note: String) {
        model.setCurrentNote(note)
        view.displayNote(model.getCurrentNote())
    }

    fun updateViewTexts() {
        view.updateStreakText(model.getCurrentStreak())
        view.updateWrongText(model.getWrongAnswers())
        view.updateCorrectText(model.getCorrectAnswers())
    }

    fun setClefMode(mode: String) {
        model.setClefMode(mode)
    }
}

