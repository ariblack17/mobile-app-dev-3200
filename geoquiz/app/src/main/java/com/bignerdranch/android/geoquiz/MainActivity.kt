package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    // communication between MainActivity and CheatActivity - we can tell here if the user cheated or not
    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }
        // listener for cheat button
        binding.cheatButton.setOnClickListener {
            // find correct answer
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            // generate intent to communicate with CheatActivity
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            // launch cheat launcher with intent (tell main activity we're cheating)
            cheatLauncher.launch(intent)
        }

        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        // decide which string to generate
        val messageResId = when {
            quizViewModel.currentQuestionAnswered -> R.string.move_on_toast // tell user to move on if prev answered
            quizViewModel.isCheater -> R.string.judgement_toast             // show this if cheater
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        // generate toast
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        // for score keeping and move_on_toast
        if (!quizViewModel.currentQuestionAnswered && userAnswer == correctAnswer) {
            quizViewModel.currentScore++                    // increment score if correct 1st time
            quizViewModel.currentQuestionAnswered = true    // set answered = true
//            Handler().postDelayed({                         // start timer for move on toast
//                if(quizViewModel.currentQuestionAnswered == true) { Toast.makeText(this, R.string.move_on_toast, Toast.LENGTH_SHORT).show() }, 10000)
//            }
//                    Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
//                Toast.makeText(this, R.string.move_on_toast, Toast.LENGTH_SHORT).show()
//                                                                                  }, 10000)

        }
        val r = Runnable {
            if(quizViewModel.currentQuestionAnswered == true) {
                Toast.makeText(this, R.string.move_on_toast, Toast.LENGTH_SHORT).show()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed(r, 10000)

        // for displaying score
        if (quizViewModel.currentIndex == quizViewModel.numQuestions - 1) {    // if last question
            if (quizViewModel.isCheater) Toast.makeText(this, "Score: 0. You Cheated!", Toast.LENGTH_LONG)
                .show()
            else {
                Toast.makeText(this, "Score: " + quizViewModel.currentScore + " / " + quizViewModel.numQuestions, Toast.LENGTH_LONG).show()
            }
        }
    }
}
