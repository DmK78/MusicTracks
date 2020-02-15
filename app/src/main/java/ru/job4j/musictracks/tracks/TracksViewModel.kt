package ru.job4j.musictracks.tracks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.job4j.musictracks.models.GetTracksResponse
import ru.job4j.vkfriendskt.network.NetworkService

class TracksViewModel(application: Application) : AndroidViewModel(application) {
    lateinit var networkService: NetworkService
    private val tracksMutableLiveData = MutableLiveData<GetTracksResponse>()

    init {
        networkService = NetworkService()
    }

    fun findTracks(name: String) {
        networkService.getTracks(name, tracksMutableLiveData)

    }

    fun getTracksMutableLiveData() = tracksMutableLiveData
}