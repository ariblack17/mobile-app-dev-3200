package com.bignerdranch.android.geoquiz

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

//private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val IS_CHEATER_KEY = "IS_CHEATER_KEY"
const val CURRENT_SCORE_KEY = "CURRENT_SCORE_KEY"
const val QUESTION_ANSWERED_KEY = "QUESTION_ANSWERED_KEY"
class QuizViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var isCheater: Boolean
        get() {
            return savedStateHandle.get(IS_CHEATER_KEY) ?: false
        }
        set(value) = savedStateHandle.set(IS_CHEATER_KEY, value)

    var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

     var currentScore: Int
        get() { return savedStateHandle.get(CURRENT_SCORE_KEY) ?: 0 }
        set(value) = savedStateHandle.set(CURRENT_SCORE_KEY, value)

    var currentQuestionAnswered: Boolean
        get() {
            return savedStateHandle.get(QUESTION_ANSWERED_KEY) ?: false
        }
        set(value) = savedStateHandle.set(QUESTION_ANSWERED_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val numQuestions: Int
        get() = questionBank.size

    fun moveToNext() {
        if(currentIndex + 1 < questionBank.size) {
            currentIndex++
            currentQuestionAnswered = false     // set current question to unanswered
        }

    }



}
