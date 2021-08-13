package com.lucasdonato.pokemon_api.presentation.home.presenter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.PokemonList
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.data.useCase.PokemonUseCase
import com.lucasdonato.pokemon_api.mechanism.currency.PaginationListener
import com.lucasdonato.pokemon_api.mechanism.livedata.MutableLiveDataResource
import com.lucasdonato.pokemon_api.mechanism.livedata.Resource
import com.lucasdonato.pokemon_api.presentation.AppApplication
import com.lucasdonato.pokemon_api.presentation.base.presenter.BasePresenter
import kotlinx.android.synthetic.main.activity_home.*

class HomePresenter(
    private val pokemonUseCase: PokemonUseCase
) : BasePresenter() {

    val getListLiveData = MutableLiveDataResource<List<Results>>()
    val searchPokemon = MutableLiveDataResource<Pokemon>()
    private var offSet: Int = 0
    var limit: Int = 20

    fun getList() = runCoroutine {
        getListLiveData.postValue(Resource.loading())
        pokemonUseCase.getPokemonData(limit, offSet)?.let {
            getListLiveData.postValue(Resource.success(it.results))
        } ?: getListLiveData.postValue(Resource.error())
    } onError {
        getListLiveData.postValue(
            Resource.error(AppApplication.context?.getString(it.errorCode.stringCode))
        )
    }

    fun getSearchPokemon(name: String) = runCoroutine {
        searchPokemon.postValue(Resource.loading())
        pokemonUseCase.getSearchPokemon(name)?.let {
            searchPokemon.postValue(Resource.success(it))
        } ?: searchPokemon.postValue(Resource.error())
    } onError {
        searchPokemon.postValue(
            Resource.error(AppApplication.context?.getString(it.errorCode.stringCode))
        )
    }

}











