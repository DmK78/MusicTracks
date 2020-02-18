package ru.job4j.vkfriendskt.network

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.job4j.musictracks.models.GetTracksResponse

/**
 * @author Dmitry Kolganov (mailto:dmk78@inbox.ru)
 * @version $Id$
 * @since 12.02.2020
 */
class NetworkService {
    private val mRetrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client =
            OkHttpClient.Builder().addInterceptor(interceptor).build()
        mRetrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getTracks(
        name: String, callback: MutableLiveData<GetTracksResponse>
    ) {
        jSONApi.getUserFriends(name, 30)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<GetTracksResponse?>() {
                override fun onSuccess(getTracksResponse: GetTracksResponse) {
                    callback.postValue(getTracksResponse)
                }

                override fun onError(e: Throwable) {}
            })
    }

    private val jSONApi: JSonHolderApi
        get() = mRetrofit.create(JSonHolderApi::class.java)

    companion object {
        private const val BASE_URL = "https://itunes.apple.com/"
    }


}