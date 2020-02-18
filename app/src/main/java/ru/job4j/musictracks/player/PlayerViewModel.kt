package ru.job4j.musictracks.player

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ru.job4j.musictracks.player.AudioService.MyBinder


var mediaPlayer: MediaPlayer? = null

class PlayerViewModel(application: Application) : AndroidViewModel(application),
    MediaPlayer.OnPreparedListener, Runnable {

    var isPlay = false
    val LOG_TAG = "myLogs"
    var bound = false
    var sConn: ServiceConnection? = null
    var intent: Intent? = null
    var audioService: AudioService? = null

    init {
        intent = Intent(application, AudioService::class.java)
        sConn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected")
                audioService = (binder as MyBinder).service
                bound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected")
                bound = false
            }
        }


    }

    override fun onCleared() {
        super.onCleared()
    }

    fun initPlayer(trackUrl: String) {
        intent?.putExtra("trackUrl", trackUrl)



    }


    override fun run() {

    }

    override fun onPrepared(p0: MediaPlayer?) {

    }


}