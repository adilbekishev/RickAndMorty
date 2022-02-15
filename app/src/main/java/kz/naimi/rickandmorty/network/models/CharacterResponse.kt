package kz.naimi.rickandmorty.network.models

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    val info: Info,
    @SerializedName("results")
    val characters: List<Character>
)