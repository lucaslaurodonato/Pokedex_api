package com.lucasdonato.pokemon_api.data.repository.apod

import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.PokemonList
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.data.remote.dataSource.PokemonDataSource
import com.lucasdonato.pokemon_api.data.repository.performRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(private val pokemonDataSource: PokemonDataSource) {

    suspend fun getPokemonDate(limit: Int,offSet: Int) = withContext(Dispatchers.IO) {
        (performRequest(
            pokemonDataSource.getPokemonList(limit, offSet).execute(), true) as PokemonList?)
    }

    suspend fun getPokemonDetails(id: Int) = withContext(Dispatchers.IO) {
        (performRequest(
            pokemonDataSource.getPokemon(id).execute(), true) as Pokemon?)
    }

}