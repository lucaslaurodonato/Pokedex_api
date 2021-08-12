package com.lucasdonato.pokemon_api.data.useCase

import com.lucasdonato.pokemon_api.data.repository.apod.PokemonRepository
import com.lucasdonato.pokemon_api.domain.base.runSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonUseCase(private val pokemonRepository: PokemonRepository) {

    suspend fun getPokemonData(limit: Int, offSet: Int) = withContext(Dispatchers.IO) {
        runSuspend {
            pokemonRepository.getPokemonDate(limit, offSet)
        }
    }

    suspend fun getPokemonDetails(id: Int) = withContext(Dispatchers.IO) {
        runSuspend {
            pokemonRepository.getPokemonDetails(id)
        }
    }

    suspend fun getSearchPokemon(name: String) = withContext(Dispatchers.IO) {
        runSuspend {
            pokemonRepository.getSearchPokemon(name)
        }
    }

}