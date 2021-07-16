package com.lucasdonato.pokemon_api.data.remote.dataSource

import com.lucasdonato.pokemon_api.data.remote.WebService

class PokemonDataSource(private val webService: WebService) {

    fun getPokemonList(limit: Int, offSet: Int) = webService.getList(limit, offSet)

    fun getPokemon(id: Int) = webService.getPokemon(id)

}