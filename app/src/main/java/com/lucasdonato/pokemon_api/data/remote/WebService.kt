package com.lucasdonato.pokemon_api.data.remote

import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.PokemonList
import com.lucasdonato.pokemon_api.data.model.Results
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    @GET("pokemon")
    fun getList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonList>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<Pokemon>

}