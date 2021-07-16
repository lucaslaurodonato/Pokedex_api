package com.lucasdonato.pokemon_api.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PokemonList(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: String,
    @SerializedName("results") val results: List<Results>
) : Serializable

