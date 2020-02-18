package ru.job4j.musictracks.player

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class AudioService : Service(), MediaPlayer.OnPreparedListener, Runnable {
    private var binder: MyBinder = MyBinder()
    private val LOG_TAG = "myLogs"
    private var mediaPlayer: MediaPlayer? = null
    private var trackUrl: String? = ""
    var isPlaybackShouldContinuePlaying: Boolean = false
    private var progressLiveData: MutableLiveData<Int> = MutableLiveData()

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "MyService onCreate")
    }

    fun isPlaying(): Boolean {
        mediaPlayer?.let {
            return mediaPlayer!!.isPlaying
        }
        return false
    }

    fun getProgressLiveData(): MutableLiveData<Int> = progressLiveData

    fun play() {
        mediaPlayer?.start()
        isPlaybackShouldContinuePlaying = true
        Thread(this).start()
    }

    fun pause() {
        mediaPlayer?.pause()
        isPlaybackShouldContinuePlaying = false
    }

    fun freeze() {
        mediaPlayer?.pause()
        isPlaybackShouldContinuePlaying = true
    }

    fun seekTo(position: Int) {
        mediaPlayer!!.seekTo(position * 1000)
    }

    fun getPosition(): Int = mediaPlayer!!.currentPosition / 1000

    fun getDuration(): Int = mediaPlayer!!.duration / 1000

    override fun onBind(arg0: Intent?): IBinder? {
        Log.d(LOG_TAG, "MyService onBind")

        val extras = arg0!!.extras
        if (extras == null) Log.d("Service", "null") else {
            Log.d("Service", "not null")
            trackUrl = extras["trackUrl"] as String?
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer!!.setDataSource(trackUrl)
                mediaPlayer!!.prepareAsync()
            }
        }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            mediaPlayer!!.release()
        }
        return super.onUnbind(intent)
    }

    inner class MyBinder : Binder() {
        val service: AudioService
            get() {
                return this@AudioService
            }
    }

    override fun onPrepared(p0: MediaPlayer?) {
        Log.d(LOG_TAG, "MyService is ready")
    }

    override fun run() {
        var currentPosition = mediaPlayer!!.currentPosition / 1000
        val total = mediaPlayer!!.duration / 1000
        while (mediaPlayer!!.isPlaying() && currentPosition < total) {
            currentPosition = try {
                Thread.sleep(500)
                mediaPlayer!!.currentPosition / 1000
            } catch (e: InterruptedException) {
                return
            } catch (e: java.lang.Exception) {
                return
            }
            progressLiveData.postValue(currentPosition)
            Log.d(LOG_TAG, "current position is $currentPosition")
        }
    }
}