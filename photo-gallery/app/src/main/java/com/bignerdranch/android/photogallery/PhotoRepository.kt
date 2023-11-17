// holds retrofit config and api direct access code

package com.bignerdranch.android.photogallery

import com.bignerdranch.android.photogallery.api.FlickrApi
import com.bignerdranch.android.photogallery.api.GalleryItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class PhotoRepository {
    // retrofit config and api interface init
    private val flickrApi: FlickrApi
    init {
        // build retrofit instance
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")                     // set url
            .addConverterFactory(MoshiConverterFactory.create())    // add converter to object
            .build()                                                // build instance
        // create concrete implementation of flickerapi with retrofit instance
        flickrApi = retrofit.create()
    }
    // wrap retrofit api function for fetching flickr homepage
    suspend fun fetchPhotos() : List<GalleryItem> = flickrApi.fetchPhotos().photos.galleryItems
}