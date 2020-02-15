package ru.job4j.musictracks.player

import android.R
import android.app.Application
import android.media.MediaPlayer
import android.util.MutableBoolean
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class PlayerViewModel(application: Application)
    : AndroidViewModel(application) {
    var mediaPlayer: MediaPlayer? = null
    private var isPlayLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun initPlayer(trackUrl: String){
        if (mediaPlayer==null){
            mediaPlayer= MediaPlayer()
            isPlayLiveData.postValue(mediaPlayer?.isPlaying)
            mediaPlayer!!.setDataSource(trackUrl)
            mediaPlayer!!.prepare()
            //    mediaPlayer!!.setVolume(0.5f, 0.5f)
            mediaPlayer!!.isLooping = false
        }

    }

    fun getIsPlayingLiveData() = isPlayLiveData

    fun play(){
        mediaPlayer!!.start()
        isPlayLiveData.postValue(true)

    }

    fun stop(){

        mediaPlayer!!.pause()
        isPlayLiveData.postValue(false)

    }

    fun clearMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer!!.release()
                //mediaPlayer = null
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }


        /*  mediaPlayer.stop()
          mediaPlayer.release()
          mediaPlayer = null!!*/
    }


}