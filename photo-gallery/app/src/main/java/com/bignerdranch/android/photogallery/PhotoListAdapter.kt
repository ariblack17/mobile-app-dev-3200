// holds onto an instance of the view and binds gallery item to it
// manages communication between recycler view and backing data

package com.bignerdranch.android.photogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.bignerdranch.android.photogallery.api.GalleryItem
import com.bignerdranch.android.photogallery.databinding.ListItemGalleryBinding

class PhotoViewHolder(private val binding: ListItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {
    // viewholder implementation
    fun bind(galleryItem: GalleryItem) {
        // load image
        binding.itemImageView.load(galleryItem.url) {
//            placeholder(R.drawable.img_5839)            // the placeholder image while photos load
            placeholder(R.mipmap.headshot_round)        // the circular placeholder image while photos load
            transformations(CircleCropTransformation()) // crop the images to be circular
        }
    }
}

// added code below to line 18 and below to change image placeholder and shape

// provide photoviewholders as needed, based on list of galleryitems
class PhotoListAdapter(private val galleryItems: List<GalleryItem>) : RecyclerView.Adapter<PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = galleryItems[position]
        holder.bind(item)
    }

    override fun getItemCount() = galleryItems.size
}