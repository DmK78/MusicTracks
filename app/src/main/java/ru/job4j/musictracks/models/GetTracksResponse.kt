package ru.job4j.musictracks.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetTracksResponse(
    @SerializedName("resultCount")
    @Expose
    var resultCount: Int? = null,
    @SerializedName("results")
    @Expose
    var results: List<Track>? = null
)