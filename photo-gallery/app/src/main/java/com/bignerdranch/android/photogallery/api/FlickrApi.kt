// retrofit api interface

package com.bignerdranch.android.photogallery.api

import retrofit2.http.GET

private const val API_KEY = "e2afc253b6876e7a83625d5265656886"

interface FlickrApi {
    // get request
    @GET(
        // fetch recent interesting photos
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    suspend fun fetchPhotos(): FlickrResponse
}