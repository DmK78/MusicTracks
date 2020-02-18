package ru.job4j.musictracks.player

import android.app.Service
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import java.util.*


class AudioService : Service() , MediaPlayer.OnPreparedListener{
    private var binder: MyBinder = MyBinder()
    val LOG_TAG = "myLogs"
    var mediaPlayer: MediaPlayer? = null
    var timer: Timer? = null
    var tTask: TimerTask? = null
    var interval: Long = 1000
    var trackUrl: String? = ""

    override fun onCreate() {
        super.onCreate()
        Log.d(LOG_TAG, "MyService onCreate")
        timer = Timer()
        //schedule()

    }

    fun schedule() {
        tTask?.let { tTask!!.cancel() }
        if (interval > 0) {
            tTask = object : TimerTask() {
                override fun run() {
                    Log.d(LOG_TAG, "run")
                }
            }
            timer?.schedule(tTask, 1000, interval)
        }
    }

    fun upInterval(gap: Long): Long {
        interval = interval + gap
        schedule()
        return interval
    }

    fun isPlaying(): Boolean = mediaPlayer?.let { mediaPlayer?.isPlaying }!!

    fun play() = mediaPlayer?.start()

    fun pause() = mediaPlayer?.pause()

    fun seetTo(position: Int){
        mediaPlayer!!.seekTo(position)
    }

    fun getPosition():Int  = mediaPlayer!!.currentPosition

    fun getDuration():Int  = mediaPlayer!!.duration



    fun downInterval(gap: Long): Long {
        interval = interval - gap
        if (interval < 0) interval = 0
        schedule()
        return interval
    }

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


           // mediaPlayer!!.start()
        }



        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            //mediaPlayer.release()
        }
        return super.onUnbind(intent)
    }

    override fun onStart(intent: Intent?, startId: Int) {
        super.onStart(intent, startId)
        Log.d(LOG_TAG, "MyService onStart")
        /*val extras = intent!!.extras
        if (extras == null) Log.d("Service", "null") else {
            Log.d("Service", "not null")
            trackUrl = extras["trackUrl"] as String?
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(trackUrl)
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare()
            //mediaPlayer.start()
        }*/

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
}