package com.lucasdonato.pokemon_api.presentation.details.presenter

import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.PokemonList
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.data.useCase.PokemonUseCase
import com.lucasdonato.pokemon_api.mechanism.livedata.MutableLiveDataResource
import com.lucasdonato.pokemon_api.mechanism.livedata.Resource
import com.lucasdonato.pokemon_api.presentation.AppApplication
import com.lucasdonato.pokemon_api.presentation.base.presenter.BasePresenter

class DetailsPresenter(
    private val pokemonUseCase: PokemonUseCase
) : BasePresenter() {

    val getListLiveData = MutableLiveDataResource<Pokemon>()

    fun getPokemonDetails(id: Int) = runCoroutine {
        getListLiveData.postValue(Resource.loading())
        pokemonUseCase.getPokemonDetails(id)?.let {
            getListLiveData.postValue(Resource.success(it))
        } ?: getListLiveData.postValue(Resource.error())
    } onError {
        getListLiveData.postValue(
            Resource.error(AppApplication.context?.getString(it.errorCode.stringCode))
        )
    }

}











