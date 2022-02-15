package kz.naimi.rickandmorty.network

import kz.naimi.rickandmorty.network.models.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): CharacterResponse
}