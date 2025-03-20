package com.example.mobilou3

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log

class Model(private var controller: Controller) : Parcelable {

    private lateinit var notesImages: HashMap<String, Int>
    private var currentNote: String = "C4_Treble"
    private var correctAnswers: Int = 0
    private var wrongAnswers: Int = 0
    private var currentStreak: Int = 0
    private var trebleClefOnly: Boolean = false
    private var clefMode: String = "Both"

    init {
        initNotesImages()
    }

    fun getCurrentStreak(): Int {
        return currentStreak
    }

    fun setClefMode(mode: String) {
        clefMode = mode
    }

    fun getRandomNoteImage(): String {
        val filteredNotes = when (clefMode) {
            "Treble" -> notesImages.keys.filter { it.contains("Treble") }
            "Bass" -> notesImages.keys.filter { it.contains("Bass") }
            else -> notesImages.keys.toList()
        }
        currentNote = filteredNotes.random()
        return currentNote
    }

    fun getCorrectAnswers(): Int {
        return correctAnswers
    }

    fun setCorrectAnswers(value: Int) {
        Log.d("GameState", "setCorrectAnswers() called with value: $value")
        correctAnswers = value
    }

    fun getWrongAnswers():Int {
        return wrongAnswers
    }

    fun setWrongAnswers(value: Int) {
        wrongAnswers = value
    }

    fun getCurrentNote(): String {
        Log.d("GameState", "getCorrectAnswers() returning: $correctAnswers")
        return currentNote
    }
    fun setCurrentNote(note: String) {
        currentNote = note
    }


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

    constructor(parcel: Parcel) : this(Controller()) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    fun setCurrentStreak(value: Int) {
        currentStreak = value
    }

    fun setTrebleClefOnly(enabled: Boolean) {
        trebleClefOnly = enabled
    }

    companion object CREATOR : Parcelable.Creator<Model> {
        override fun createFromParcel(parcel: Parcel): Model {
            return Model(parcel)
        }

        override fun newArray(size: Int): Array<Model?> {
            return arrayOfNulls(size)
        }
    }
}