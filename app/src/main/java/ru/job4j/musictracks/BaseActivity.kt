package ru.job4j.musictracks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */
abstract class BaseActivity : AppCompatActivity() {
    protected abstract fun createFragment(): Fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.container)
        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction()
                .add(R.id.container, fragment)
                .commit()
        }
    }
}