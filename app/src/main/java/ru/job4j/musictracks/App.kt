package ru.job4j.musictracks

import android.app.Application
import ru.job4j.vkfriendskt.di.AppComponent
import ru.job4j.vkfriendskt.di.DaggerAppComponent

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }

    companion object {
        private var appComponent: AppComponent? = null

        fun getComponent(): AppComponent? = appComponent
    }
}