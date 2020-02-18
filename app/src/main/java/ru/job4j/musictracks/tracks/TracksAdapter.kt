package ru.job4j.musictracks.tracks

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_track.view.*
import ru.job4j.musictracks.R
import ru.job4j.musictracks.models.Track


/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TracksAdapter(
    private val itemClick: (Track) -> Unit
) : RecyclerView.Adapter<TracksAdapter.TracksHolder>() {
    private val tracks: ArrayList<Track> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TracksHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_track,
            parent, false
        )
        return TracksHolder(
            itemView
        )
    }

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: TracksHolder, position: Int) {
        val currentTrack = tracks[position]
        holder.setup(currentTrack, itemClick)
    }

    fun setData(tracksList: List<Track>) {
        tracks.clear()
        tracks.addAll(tracksList)
        notifyDataSetChanged()
    }

    class TracksHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun setup(track: Track, itemClick: (Track) -> Unit) {
            with(containerView) {
                tracksTvTrackName.text = track.trackName
                tracksTvArtistName.text = track.artistName
                track.artworkUrl100?.let {
                    val context: Context = tracksIvLogo.getContext()
                    Picasso.with(context).load(track.artworkUrl100)
                        .into(tracksIvLogo)
                }
                setOnClickListener { itemClick.invoke(track) }
            }
        }
    }
}