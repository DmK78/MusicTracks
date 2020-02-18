package ru.job4j.vkfriendskt.di

import dagger.Component
import ru.job4j.musictracks.tracks.TracksViewModel

import javax.inject.Singleton

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */

@Singleton
@Component(modules = [NetworkServiceModule::class])
interface AppComponent {
    fun injectTo(friendsListViewModel: TracksViewModel?)

}