package ru.job4j.vkfriendskt.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.job4j.musictracks.models.GetTracksResponse

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */
interface JSonHolderApi {
    @GET("search")
    fun getUserFriends(
        @Query("term") name: String,
        @Query("limit") limit: Int

    ): Single<GetTracksResponse>


}