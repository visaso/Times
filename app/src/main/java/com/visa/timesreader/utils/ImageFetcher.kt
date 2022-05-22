package com.visa.timesreader.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageFetcher {

    /**
     * Fetch image for article and resize if the element is not highlighted.
     */
    fun fetchCoverImage(isHighlighted: Boolean, url: String, view: ImageView) {
        if (isHighlighted) {
            Picasso.get().load(url).fit().centerCrop().into(view)
        } else {
            Picasso.get().load(url).resize(184, 184).centerCrop().into(view)
        }
    }
}