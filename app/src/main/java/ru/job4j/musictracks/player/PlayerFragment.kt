package ru.job4j.musictracks.player


import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_player.*
import ru.job4j.musictracks.tracks.TracksFragment


class PlayerFragment : Fragment(), Runnable {

    //var mediaPlayer: MediaPlayer? = MediaPlayer()
    var mSeekBar: SeekBar? = null
    //var wasPlaying = false
    var fab: FloatingActionButton? = null
    private lateinit var viewModel: PlayerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        val trackName = arguments!!.getString(TracksFragment.TRACK_NAME)
        val trackUrl = arguments!!.getString(TracksFragment.TRACK_URL)
        val trackImg = arguments!!.getString(TracksFragment.TRACK_IMG)
        val artistName = arguments!!.getString(TracksFragment.ARTIST_NAME)
         viewModel.initPlayer(trackUrl!!)
        trackImg?.let {

            val context: Context = playerIvAlbumImg.context
            Picasso.with(context).load(trackImg)
                .into(playerIvAlbumImg)
        }
        playerTvArtistName.text = artistName
        playerTvTracktName.text = trackName

      /*  viewModel.getIsPlayingLiveData().observe(viewLifecycleOwner, Observer {
            if(it==true){
                viewModel.play()
                fab!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_media_pause
                    )
                )
            } else
            {
                viewModel.stop()
                fab!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_media_play
                    )
                )
            }
        })*/

        fab = button as FloatingActionButton?
        fab!!.setOnClickListener {
            if (viewModel.mediaPlayer!!.isPlaying){
                viewModel.stop()
            } else{
                viewModel.play()
            }
        }


        val seekBarHint: TextView = textView

        /*mSeekBar = seekbar

        mSeekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                seekBarHint.visibility = View.VISIBLE
            }

            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromTouch: Boolean
            ) {
                seekBarHint.visibility = View.VISIBLE
                val x = Math.ceil(progress / 1000f.toDouble()).toInt()
                if (x < 10) seekBarHint.text = "0:0$x" else seekBarHint.text = "0:$x"
                val percent = progress / seekBar.max.toDouble()
                val offset = seekBar.thumbOffset
                val seekWidth = seekBar.width
                val `val` = Math.round(percent * (seekWidth - 2 * offset)).toInt()
                val labelWidth = seekBarHint.width
                seekBarHint.x = (offset + seekBar.x + `val` - Math.round(percent * offset)
                        - Math.round(percent * labelWidth / 2))
                if (progress > 0 && viewModel.mediaPlayer != null && !viewModel.mediaPlayer!!.isPlaying) {
                    clearMediaPlayer()
                    fab!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_media_play
                        )
                    )
                    mSeekBar!!.setProgress(0)
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                if (viewModel.mediaPlayer != null && viewModel.mediaPlayer!!.isPlaying) {
                    viewModel.mediaPlayer!!.seekTo(seekBar.progress)
                }
            }
        })*/


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(ru.job4j.musictracks.R.layout.fragment_player, container, false)
    }

    fun playSong(trackUrl: String) {
        /*try {
            if (viewModel.mediaPlayer != null && viewModel.mediaPlayer!!.isPlaying) {
                viewModel.clearMediaPlayer()
                mSeekBar!!.progress = 0
                viewModel.wasPlaying = true
                fab!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_media_play
                    )
                )
            }
            if (!viewModel.wasPlaying) {
                if (viewModel.mediaPlayer == null) {
                    viewModel.mediaPlayer = MediaPlayer()
                }
                fab!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_media_pause
                    )
                )

                viewModel.mediaPlayer!!.setDataSource(trackUrl)


                viewModel.mediaPlayer!!.prepare()
                viewModel.mediaPlayer!!.setVolume(0.5f, 0.5f)
                viewModel.mediaPlayer!!.isLooping = false
                mSeekBar!!.max = viewModel.mediaPlayer!!.duration

                viewModel.mediaPlayer!!.start()
                Thread(this).start()
            }
            viewModel.wasPlaying = false
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
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

    override fun run() {
        var currentPosition = viewModel.mediaPlayer?.currentPosition
        val total = viewModel.mediaPlayer?.duration


        while (viewModel.mediaPlayer != null && viewModel.mediaPlayer!!.isPlaying && currentPosition!! < total!!) {
            currentPosition = try {
                Thread.sleep(1000)
                viewModel.mediaPlayer!!.currentPosition
            } catch (e: InterruptedException) {
                return
            } catch (e: java.lang.Exception) {
                return
            }
            mSeekBar!!.progress = currentPosition
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        clearMediaPlayer()
    }

    private fun clearMediaPlayer() {
        if (viewModel.mediaPlayer != null) {
            try {
                viewModel.mediaPlayer!!.release()
                //viewModel.mediaPlayer = null
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }


        /*  mediaPlayer.stop()
          mediaPlayer.release()
          mediaPlayer = null!!*/
    }

}