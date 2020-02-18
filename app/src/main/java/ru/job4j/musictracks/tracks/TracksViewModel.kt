package ru.job4j.musictracks.tracks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.job4j.musictracks.App
import ru.job4j.musictracks.models.GetTracksResponse
import ru.job4j.vkfriendskt.network.NetworkService
import javax.inject.Inject

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TracksViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var networkService: NetworkService
    private val tracksMutableLiveData = MutableLiveData<GetTracksResponse>()
    init {
        App.getComponent()?.injectTo(this)
    }

    fun findTracks(name: String) {
        networkService.getTracks(name, tracksMutableLiveData)

    }

    fun getTracksMutableLiveData() = tracksMutableLiveData
}