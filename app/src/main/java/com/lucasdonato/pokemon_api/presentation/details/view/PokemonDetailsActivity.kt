package com.lucasdonato.pokemon_api.presentation.details.view

import Stats
import Types
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.mechanism.*
import com.lucasdonato.pokemon_api.mechanism.extensions.*
import com.lucasdonato.pokemon_api.mechanism.livedata.Status
import com.lucasdonato.pokemon_api.mechanism.utils.GradientUtil
import com.lucasdonato.pokemon_api.mechanism.utils.GradientUtil.Companion.getGradientColor
import com.lucasdonato.pokemon_api.presentation.details.adapter.TypeRecyclerAdapter
import com.lucasdonato.pokemon_api.presentation.details.presenter.DetailsPresenter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.include_card_image_description.*
import kotlinx.android.synthetic.main.include_description.*
import kotlinx.android.synthetic.main.include_status_progress.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class PokemonDetailsActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context, results: Results?, pokemon: Pokemon?): Intent =
            Intent(context, PokemonDetailsActivity::class.java).apply {
                putExtra(EXTRA_RESULTS, results)
                putExtra(EXTRA_POKEMON, pokemon)
            }
    }

    private val typeList: TypeRecyclerAdapter by lazy { TypeRecyclerAdapter() }
    private val presenter: DetailsPresenter by inject { parametersOf(this) }
    private val gradientColors : MutableList<String> = mutableListOf()
    private var resultsData: Results? = null
    private var pokemonData: Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        clickListeners()
        receiveData()
        setupDataResults()
        setupSearchPokemon()
    }

    private fun receiveData() {
        resultsData = intent?.getSerializableExtra(EXTRA_RESULTS) as Results?
        pokemonData = intent?.getSerializableExtra(EXTRA_POKEMON) as Pokemon?
    }

    private fun clickListeners() {
        back.setOnClickListener { finish() }
    }

    private fun setupDataResults() {
        resultsData?.let {
            it.number?.let { number -> presenter.getPokemonDetails(number) }
            presenter.getImageInGlide(it.imageUrl, this, image_pokemon)
        }
        setupObserver()
    }

    private fun setupSearchPokemon() {
        pokemonData?.let {
            setupView(it)
            presenter.getImageInGlide(it.sprites?.front_shiny, this, image_pokemon)
        }
    }

    private fun setupObserver() {
        presenter.getListLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> loader.visible()
                Status.SUCCESS -> setupView(it.data)
                Status.ERROR -> showErrorToast()
                else -> showErrorToast()
            }
        })
        presenter.image.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> image_progress.visible()
                Status.ERROR -> errorImage()
                Status.SUCCESS -> image_progress.gone()
                else -> errorImage()
            }
        })
    }

    private fun setupView(pokemon: Pokemon?) {
        loader.gone()
        pokemon?.let {
            name.text = it.name?.capitalize()
            height.text = getString(R.string.pokemon_height, convertValue(it.height))
            weight.text = getString(R.string.pokemon_weight, convertValue(it.weight))
            it.types?.let { types -> type(types) }
            it.stats?.let { stats -> stats(stats) }

            it.types?.forEach {
                gradientColors.addAll(listOf(getGradientColor(it.type.name)))
                setupBackgroundPokemonImage(it.type.name)
            }
        }
    }
    private fun setupBackgroundPokemonImage(type: String){
        if(gradientColors.size >= 2) image_background_card.background = (GradientUtil.setBackgroundGradient(gradientColors))
        else image_background_card.setBackgroundResource(getTypeColor(type))
    }

    private fun errorImage() {
        image_progress.gone()
        image_pokemon.setImageResource(R.drawable.pikachu_surprised)
    }

    private fun showErrorToast() {
        loader.gone()
        toast(getString(R.string.error_generic))
    }

    private fun type(types: List<Types>) {
        typeList.data = types.toMutableList()
        type_recycler.apply {
            adapter = typeList
            isFocusable = false
        }
    }

    private fun stats(stats: List<Stats>) {
        ///TODO: CRIAR UM ENUM PARA COLOCAR CADA UM EM SEU INDICE
        progress_hp.progress = stats[HP].base_stat.toFloat()
        progress_hp.labelText = getString(R.string.pokemon_status_progress, stats[HP].base_stat)
        progress_atk.progress = stats[ATK].base_stat.toFloat()
        progress_atk.labelText = getString(R.string.pokemon_status_progress, stats[ATK].base_stat)
        progress_def.progress = stats[DEF].base_stat.toFloat()
        progress_def.labelText = getString(R.string.pokemon_status_progress, stats[DEF].base_stat)
        progress_spa.progress = stats[SPA].base_stat.toFloat()
        progress_spa.labelText = getString(R.string.pokemon_status_progress, stats[SPA].base_stat)
        progress_spd.progress = stats[SPD].base_stat.toFloat()
        progress_spd.labelText = getString(R.string.pokemon_status_progress, stats[SPD].base_stat)
        progress_spe.progress = stats[SPE].base_stat.toFloat()
        progress_spe.labelText = getString(R.string.pokemon_status_progress, stats[SPE].base_stat)
    }

}