package kz.naimi.rickandmorty.network

import kz.naimi.rickandmorty.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val service by lazy { retrofit.create(Api::class.java) }
}