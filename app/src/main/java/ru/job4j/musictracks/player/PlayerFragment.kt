package ru.job4j.musictracks.player


import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_player.*
import ru.job4j.musictracks.R
import ru.job4j.musictracks.tracks.TracksFragment

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class PlayerFragment : Fragment() {
    private var mSeekBar: SeekBar? = null
    private var fab: FloatingActionButton? = null
    private var bound = false
    private val LOG_TAG = "myLogs"
    private var sConn: ServiceConnection? = null
    private var intent: Intent? = null
    private var audioService: AudioService? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val trackName = arguments!!.getString(TracksFragment.TRACK_NAME)
        val trackUrl = arguments!!.getString(TracksFragment.TRACK_URL)
        val trackImg = arguments!!.getString(TracksFragment.TRACK_IMG)
        val artistName = arguments!!.getString(TracksFragment.ARTIST_NAME)
        intent = Intent(context, AudioService::class.java)
        intent!!.putExtra("trackUrl", trackUrl)
        sConn = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                Log.d(LOG_TAG, "MainActivity onServiceConnected")
                audioService = (binder as AudioService.MyBinder).service
                bound = true
                audioService!!.getProgressLiveData().observe(viewLifecycleOwner, Observer {
                    seekbar.progress = it
                    if (mSeekBar?.progress != audioService?.getDuration()) {
                        mSeekBar?.max = audioService!!.getDuration()
                    }
                    if (audioService!!.isPlaybackShouldContinuePlaying) {
                        audioService!!.play()
                        fab!!.setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.pause_button
                            )
                        )
                    }
                })
            }

            override fun onServiceDisconnected(name: ComponentName) {
                Log.d(LOG_TAG, "MainActivity onServiceDisconnected")
                bound = false
            }
        }
        trackImg?.let {
            val context: Context = playerIvAlbumImg.context
            Picasso.with(context).load(trackImg)
                .into(playerIvAlbumImg)
        }
        playerTvArtistName.text = artistName
        playerTvTracktName.text = trackName
        fab = button as FloatingActionButton?
        fab!!.setOnClickListener {
            audioService?.let {
                if (!audioService!!.isPlaying()) {
                    audioService!!.play()
                    fab!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.pause_button
                        )
                    )
                } else {
                    audioService!!.pause()
                    fab!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.play_button
                        )
                    )
                }
            }
        }

        mSeekBar = seekbar
        mSeekBar!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromTouch: Boolean
            ) {
                Log.d(
                    LOG_TAG,
                    "Seekbar  - aud-pos = ${audioService?.getPosition()} aud-max = ${audioService?.getDuration()} seek-pos = $progress seek-max = ${seekBar.max}"
                )
                if (audioService?.getPosition() == audioService?.getDuration()) {
                    seekBar.progress = 0
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                audioService!!.seekTo(seekBar.progress)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        context?.bindService(intent, sConn!!, BIND_AUTO_CREATE);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    companion object {
        fun of(
            trackUrl: String, trackName: String, trackImg: String, artistName: String
        ): PlayerFragment =
            PlayerFragment().apply {
                arguments = bundleOf(
                    TracksFragment.TRACK_URL to trackUrl,
                    TracksFragment.TRACK_NAME to trackName,
                    TracksFragment.TRACK_IMG to trackImg,
                    TracksFragment.ARTIST_NAME to artistName
                )
            }
    }

    override fun onPause() {
        super.onPause()
        audioService!!.freeze()
    }

}