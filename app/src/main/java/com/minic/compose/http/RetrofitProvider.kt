package com.minic.kt.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 描述: Retrofit
 * 作者: ChenYy
 * 日期: 2019-10-23 15:01
 */
object RetrofitProvider {
    const val CONNECT_TIME_OUT: Long = 1000 * 30
    const val WAN_ANDROID_URL: String = "https://www.wanandroid.com/"

    private val retrofit: Retrofit by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .writeTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            .build()
        Retrofit.Builder().baseUrl(WAN_ANDROID_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun <T> createService(clazz: Class<T>): T {
        return retrofit.create(clazz)
    }
}