// view holder - stores a reference to an item's view

package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import java.util.*

// view holder, takes in the binding we want, passes root view as an argument to recycler view constructor
class CrimeHolder (private val binding: ListItemCrimeBinding): RecyclerView.ViewHolder(binding.root) {
    // cache crime into a property, set crimeTitle and crimeDate, handle touch events, show image
    fun bind(crime: Crime, onCrimeClicked: (crimeId: UUID) -> Unit) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()
        // handle touch events
        binding.root.setOnClickListener { onCrimeClicked(crime.id) }
        // only show handcuffs if crime is solved
        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE } else { View.GONE
        }
    }
}
// adapter, can create view holders and bind them to items
class CrimeListAdapter(private val crimes: List<Crime>, private val onCrimeClicked: (crimeId: UUID) -> Unit) : RecyclerView.Adapter<CrimeHolder>() {
    // create a binding to display, wrap the view in a view holder, and return the result
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
        return CrimeHolder(binding)
    }
    // populate the holder with the given crime
    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        // call binding function
        holder.bind(crime, onCrimeClicked)
    }
    // return the number of items in crime list
    override fun getItemCount() = crimes.size
}