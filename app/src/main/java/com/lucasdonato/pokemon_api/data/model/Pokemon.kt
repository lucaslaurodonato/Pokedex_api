package com.lucasdonato.pokemon_api.data.model

import Abilities
import Moves
import Sprites
import Stats
import Types
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.random.Random

data class Pokemon(
    @SerializedName("name") val name: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("weight") val weight: Int?,
    @SerializedName("stats") val stats: List<Stats>?,
    @SerializedName("moves") val moves: List<Moves>?,
    @SerializedName("types") val types: List<Types>?,
    @SerializedName("sprites") val sprites: Sprites?,
    @SerializedName("abilities") val abilities: List<Abilities>?

) : Serializable



