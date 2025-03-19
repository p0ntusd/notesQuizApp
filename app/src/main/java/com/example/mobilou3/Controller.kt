package com.example.mobilou3

import android.widget.Button

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

    fun initModel() {
        model = Model(this)
    }

    fun answeredCorrect() {
        view.updateCorrectText()
        nextNote()
    }

    fun answeredWrong() {
        view.updateWrongText()
        nextNote()
    }
}