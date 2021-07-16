package com.lucasdonato.pokemon_api.presentation.home.presenter

import com.lucasdonato.pokemon_api.data.model.PokemonList
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.data.useCase.PokemonUseCase
import com.lucasdonato.pokemon_api.mechanism.livedata.MutableLiveDataResource
import com.lucasdonato.pokemon_api.mechanism.livedata.Resource
import com.lucasdonato.pokemon_api.presentation.AppApplication
import com.lucasdonato.pokemon_api.presentation.base.presenter.BasePresenter

class HomePresenter(
    private val pokemonUseCase: PokemonUseCase
) : BasePresenter() {

    val getListLiveData = MutableLiveDataResource<List<Results>>()

    fun getList(limit: Int, offSet: Int) = runCoroutine {
        getListLiveData.postValue(Resource.loading())
        pokemonUseCase.getPokemonData(limit, offSet)?.let {
            getListLiveData.postValue(Resource.success(it.results))
        } ?: getListLiveData.postValue(Resource.error())
    } onError {
        getListLiveData.postValue(
            Resource.error(AppApplication.context?.getString(it.errorCode.stringCode))
        )
    }

}











