
// cheatActivity code

package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.geoquiz.databinding.ActivityCheatBinding
const val EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false

    // on init
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // update cheat activity to use view binding
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // set global tracker boolean variable to false
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        // set up binding for show answer button
        binding.showAnswerButton.setOnClickListener {
            val answerText = when {
                // display the correct answer to the question
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    // give data from CheatActivity to MainActivity
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        // create the intent
        val data = Intent().apply {
            // add the extra
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        // send to MainActivity
        setResult(Activity.RESULT_OK, data)
    }

    // allows us to access CheatActivity functions without needing an instance of that class
    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}