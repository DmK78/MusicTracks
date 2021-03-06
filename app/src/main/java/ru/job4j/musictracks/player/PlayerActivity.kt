package ru.job4j.musictracks.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.job4j.musictracks.BaseActivity
import ru.job4j.musictracks.tracks.TracksFragment

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class PlayerActivity : BaseActivity() {
    override fun createFragment(): Fragment {
        return PlayerFragment.of(intent.getStringExtra(TracksFragment.TRACK_URL),
                    intent.getStringExtra(TracksFragment.TRACK_NAME),
                    intent.getStringExtra(TracksFragment.TRACK_IMG),
                    intent.getStringExtra(TracksFragment.ARTIST_NAME))


    }

    companion object {
        fun create(context: Context, trackUrl: String
                   , trackName: String
                   , trackImg: String
                   , artistName: String
        ): Intent {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(TracksFragment.TRACK_URL, trackUrl)
            intent.putExtra(TracksFragment.TRACK_NAME, trackName)
            intent.putExtra(TracksFragment.TRACK_IMG, trackImg)
            intent.putExtra(TracksFragment.ARTIST_NAME, artistName)
            return intent
        }
    }
}