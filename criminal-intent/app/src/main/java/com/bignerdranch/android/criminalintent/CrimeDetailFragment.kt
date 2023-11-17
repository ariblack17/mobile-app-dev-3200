// enables crime details to be displayed as separate screens

package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeDetailBinding
import kotlinx.coroutines.launch
import java.util.*

// fragment subclass
class CrimeDetailFragment : Fragment() {
    private var _binding: FragmentCrimeDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }


    private lateinit var crime: Crime

    // FIX START: initialized placeholder variables for original crime information
    private var oldTitle = ""
    private var oldDate = ""
    private var oldSolved = false

    private val args: CrimeDetailFragmentArgs by navArgs()
    private val crimeDetailViewModel: CrimeDetailViewModel by viewModels {
        CrimeDetailViewModelFactory(args.crimeId)
    }

    // inflate and bind fragment_crime_detail.xml
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding =
            FragmentCrimeDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    // wire up the fragment views
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // add a listener to EditText
        binding.apply{
            crimeTitle.doOnTextChanged { text, _, _, _ ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                oldCrime.copy(title = text.toString())
                }
            }
            // listen for CheckBox changes
            crimeSolved.setOnCheckedChangeListener { _, isChecked ->
                crimeDetailViewModel.updateCrime { oldCrime ->
                oldCrime.copy(isSolved = isChecked)
                }
            }
        }
        // update ui
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimeDetailViewModel.crime.collect { crime ->
                    crime?.let { updateUi(it) }
                }
            }
        }
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate =
            bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            crimeDetailViewModel.updateCrime { it.copy(date = newDate) }
        }
    }

    // allow view to be freed from memory when not in use
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // function to update ui
    private fun updateUi(crime: Crime) {
        binding.apply {
            // when ui is updated, set placeholder variables to initial values
            if (oldTitle == "") {
                oldTitle = crime.title
                oldDate = crime.date.toString()
                oldSolved = crime.isSolved
            }
            if (crimeTitle.text.toString() != crime.title) {
                crimeTitle.setText(crime.title)
            }
            crimeDate.text = crime.date.toString()
            crimeDate.setOnClickListener {
            findNavController().navigate(
                CrimeDetailFragmentDirections.selectDate(crime.date)
            )
        }
            // FIX HERE: added back button binding, so we can navigate back
            backButton.setOnClickListener {
                findNavController().navigate(R.id.go_back)
                // add code here to revert any changes
                crimeTitle.setText(oldTitle)
                crimeDate.text = oldDate
                crimeSolved.isChecked = oldSolved
            }
            crimeSolved.isChecked = crime.isSolved

        }
    }
}