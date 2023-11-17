// empty shell of a fragment, only logs the number of crimes found in CrimeListViewModel

package com.bignerdranch.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.criminalintent.databinding.FragmentCrimeListBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// subclass of Fragment
class CrimeListFragment : Fragment() {

    // code for hooking up view - enables null binding for onDestroyView
    private var _binding: FragmentCrimeListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    // associate the fragment with the view model
    private val crimeListViewModel: CrimeListViewModel by viewModels()
    // inflate and bind layout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCrimeListBinding.inflate(inflater, container, false)
        // use recycler view as layout manager
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
    // null out binding for onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // handle coroutine code
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                crimeListViewModel.crimes.collect { crimes ->
                    binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes) { crimeId ->
                        findNavController().navigate(CrimeListFragmentDirections.showCrimeDetail(crimeId))
                    }
                }
            }
        }
    }

}