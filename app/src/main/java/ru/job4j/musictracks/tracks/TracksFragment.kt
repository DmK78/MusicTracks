package ru.job4j.musictracks.tracks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.job4j.musictracks.R
import ru.job4j.musictracks.player.PlayerActivity

class TracksFragment : Fragment() {
    private lateinit var viewModel: TracksViewModel
    private lateinit var tracksAdapter: TracksAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_tracks, container, false)
        viewModel = ViewModelProvider(this)[TracksViewModel::class.java]
        viewModel.getTracksMutableLiveData().observe(viewLifecycleOwner, Observer {
            val tracks = it.results?.let { tracksAdapter.setData(it) }
        })
        val search: EditText = view.findViewById(R.id.editTextSearch)
        search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                p0?.let { if (p0.length > 4) viewModel.findTracks(p0.toString()) }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
        setupAdapter(view)




        return view
    }

    fun setupAdapter(view: View) {
        val recyclerView = view.findViewById(R.id.recyclerTracks) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        tracksAdapter = TracksAdapter {
            startActivity(PlayerActivity.create(view.context, it.previewUrl!!, it.trackName!!, it.artworkUrl100!!,it.artistName!!))
        }
        recyclerView.adapter = tracksAdapter
    }

    companion object {
        val TRACK_URL: String = "track_id"
        val TRACK_IMG: String = "track_img"
        val ARTIST_NAME: String = "artist_name"
        val TRACK_NAME: String = "track_name"
    }

}