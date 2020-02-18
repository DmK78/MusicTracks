package ru.job4j.vkfriendskt.di

import dagger.Module
import dagger.Provides
import ru.job4j.vkfriendskt.network.NetworkService
import javax.inject.Singleton

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */


@Module
class NetworkServiceModule {
    @Singleton
    @Provides
    fun providesNetworkService(): NetworkService {
        return NetworkService()
    }
}