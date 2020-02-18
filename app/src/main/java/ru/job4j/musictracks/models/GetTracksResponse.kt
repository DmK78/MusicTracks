package ru.job4j.musictracks.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

data class GetTracksResponse(
    @SerializedName("resultCount")
    @Expose
    var resultCount: Int? = null,
    @SerializedName("results")
    @Expose
    var results: List<Track>? = null
)