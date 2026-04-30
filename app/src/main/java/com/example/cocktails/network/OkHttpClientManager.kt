package com.example.cocktails.network

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient

object OkHttpClientManager {
    fun getOkHttpClient (context: Context): OkHttpClient {
        val cache = Cache(context.cacheDir, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor { chain ->
                var request = chain.request()
                if (!isNetworkAvailable(context)) {
                    request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=604800")
                        .build()
                }
                chain.proceed(request)
            }
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header("Cache-control", "public, max-age=86400")
                    .build()
            }
            .build()
        }
    }