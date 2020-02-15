package ru.job4j.musictracks.tracks

import ru.job4j.musictracks.BaseActivity

class TracksActivity : BaseActivity() {
    override fun createFragment() =
        TracksFragment()


}
