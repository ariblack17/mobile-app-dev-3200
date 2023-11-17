// photo gallery fragment code

package com.bignerdranch.android.photogallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import com.bignerdranch.android.photogallery.databinding.FragmentPhotoGalleryBinding
import kotlinx.coroutines.launch


private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {
    private var _binding: FragmentPhotoGalleryBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }
    private val photoGalleryViewModel: PhotoGalleryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // inflate and bind layout
        _binding =
            FragmentPhotoGalleryBinding.inflate(inflater, container, false)
        // set recycler view's layout manager to GridLayoutManager
        binding.photoGrid.layoutManager = GridLayoutManager(context, 3) // 3 images per row
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // implement and instantiate flickrapi interface
        super.onViewCreated(view, savedInstanceState)
        // launch coroutine and fetch contents on background thread
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                photoGalleryViewModel.galleryItems.collect { items ->
                    binding.photoGrid.adapter = PhotoListAdapter(items)     // add adapter to recycler view when data is avail/changed
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}