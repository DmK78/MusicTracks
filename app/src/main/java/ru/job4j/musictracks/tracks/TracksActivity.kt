package ru.job4j.musictracks.tracks

import ru.job4j.musictracks.BaseActivity

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class TracksActivity : BaseActivity() {
    override fun createFragment() =
        TracksFragment()


}
