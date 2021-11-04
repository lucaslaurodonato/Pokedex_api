package com.lucasdonato.pokemon_api.presentation.details.presenter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.PokemonList
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.data.useCase.PokemonUseCase
import com.lucasdonato.pokemon_api.mechanism.livedata.MutableLiveDataResource
import com.lucasdonato.pokemon_api.mechanism.livedata.Resource
import com.lucasdonato.pokemon_api.presentation.AppApplication
import com.lucasdonato.pokemon_api.presentation.base.presenter.BasePresenter
import kotlinx.android.synthetic.main.include_card_image_description.*

class DetailsPresenter(
    private val pokemonUseCase: PokemonUseCase
) : BasePresenter() {

    val getListLiveData = MutableLiveDataResource<Pokemon>()
    val image = MutableLiveDataResource<String>()

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











