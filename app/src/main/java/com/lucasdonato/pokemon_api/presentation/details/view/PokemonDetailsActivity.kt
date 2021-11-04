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
import com.lucasdonato.pokemon_api.databinding.ActivityDetailsBinding
import com.lucasdonato.pokemon_api.mechanism.*
import com.lucasdonato.pokemon_api.mechanism.extensions.*
import com.lucasdonato.pokemon_api.mechanism.livedata.Status
import com.lucasdonato.pokemon_api.mechanism.utils.GradientUtil
import com.lucasdonato.pokemon_api.mechanism.utils.GradientUtil.Companion.getGradientColor
import com.lucasdonato.pokemon_api.mechanism.utils.Utils
import com.lucasdonato.pokemon_api.presentation.base.view.BaseActivity
import com.lucasdonato.pokemon_api.presentation.details.adapter.TypeRecyclerAdapter
import com.lucasdonato.pokemon_api.presentation.details.presenter.DetailsPresenter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.include_card_image_description.*
import kotlinx.android.synthetic.main.include_description.*
import kotlinx.android.synthetic.main.include_status_progress.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class PokemonDetailsActivity : BaseActivity<ActivityDetailsBinding>(R.layout.activity_details) {

    companion object {
        fun getStartIntent(context: Context, results: Results?, pokemon: Pokemon?): Intent =
            Intent(context, PokemonDetailsActivity::class.java).apply {
                putExtra(EXTRA_RESULTS, results)
                putExtra(EXTRA_POKEMON, pokemon)
            }
    }

    private val presenter: DetailsPresenter by inject()
    private val typeList: TypeRecyclerAdapter by lazy { TypeRecyclerAdapter() }
    private val gradientColors: MutableList<String> = mutableListOf()
    private var resultsData: Results? = null
    private var pokemonData: Pokemon? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.imageDescription.back.setOnClickListener { finish() }
    }

    private fun setupDataResults() {
        resultsData?.let {
            it.number?.let { number -> presenter.getPokemonDetails(number) }
            Utils.setImageUrl(it.imageUrl, binding.imageDescription.imagePokemon, binding.imageDescription.imageProgress)
        }
        setupObserver()
    }

    private fun setupSearchPokemon() {
        pokemonData?.let {
            setupView(it)
            Utils.setImageUrl(it.sprites?.front_shiny, binding.imageDescription.imagePokemon, binding.imageDescription.imageProgress)
        }
    }

    private fun setupObserver() {
        presenter.getListLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> binding.loader.visible()
                Status.SUCCESS -> setupView(it.data)
                Status.ERROR -> showErrorToast()
                else -> showErrorToast()
            }
        })
        presenter.image.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> binding.imageDescription.imageProgress.visible()
                Status.ERROR -> errorImage()
                Status.SUCCESS -> binding.imageDescription.imageProgress.gone()
                else -> errorImage()
            }
        })
    }

    private fun setupView(pokemon: Pokemon?) {
        binding.loader.gone()
        pokemon?.let {
            binding.includeDescription.name.text = it.name?.capitalize()
            binding.includeDescription.height.text =
                getString(R.string.pokemon_height, convertValue(it.height))
            binding.includeDescription.weight.text =
                getString(R.string.pokemon_weight, convertValue(it.weight))
            it.types?.let { types -> type(types) }
            it.stats?.let { stats -> stats(stats) }
            it.types?.forEach {
                gradientColors.addAll(listOf(getGradientColor(it.type.name)))
                setupBackgroundPokemonImage(it.type.name)
            }
        }
    }

    private fun setupBackgroundPokemonImage(type: String) {
        if (gradientColors.size >= 2) binding.imageDescription.imageBackgroundCard.background =
            (GradientUtil.setBackgroundGradient(gradientColors))
        else binding.imageDescription.imageBackgroundCard.setBackgroundResource(getTypeColor(type))
    }

    private fun errorImage() {
        binding.imageDescription.imageProgress.gone()
        binding.imageDescription.imageBackgroundCard.setImageResource(R.drawable.pikachu_surprised)
    }

    private fun showErrorToast() {
        binding.loader.gone()
        toast(getString(R.string.error_generic))
    }

    private fun type(types: List<Types>) {
        typeList.data = types.toMutableList()
        binding.includeDescription.typeRecycler.apply {
            adapter = typeList
        }
    }

    private fun stats(stats: List<Stats>) {
        stats.getOrNull(HP)?.let {
            binding.includeDescription.includeStatusProgress.progressHp.progress =
                it.base_stat.toFloat()
            binding.includeDescription.includeStatusProgress.progressHp.labelText =
                getString(R.string.pokemon_status_progress, it.base_stat)
        }
        stats.getOrNull(ATK)?.let {
            binding.includeDescription.includeStatusProgress.progressAtk.progress =
                it.base_stat.toFloat()
            binding.includeDescription.includeStatusProgress.progressAtk.labelText =
                getString(R.string.pokemon_status_progress, it.base_stat)
        }
        stats.getOrNull(DEF)?.let {
            binding.includeDescription.includeStatusProgress.progressDef.progress =
                it.base_stat.toFloat()
            binding.includeDescription.includeStatusProgress.progressDef.labelText =
                getString(R.string.pokemon_status_progress, it.base_stat)
        }
        stats.getOrNull(SPA)?.let {
            binding.includeDescription.includeStatusProgress.progressSpa.progress =
                it.base_stat.toFloat()
            binding.includeDescription.includeStatusProgress.progressSpa.labelText =
                getString(R.string.pokemon_status_progress, it.base_stat)
        }
        stats.getOrNull(SPD)?.let {
            binding.includeDescription.includeStatusProgress.progressSpd.progress =
                it.base_stat.toFloat()
            binding.includeDescription.includeStatusProgress.progressSpd.labelText =
                getString(R.string.pokemon_status_progress, it.base_stat)
        }
        stats.getOrNull(SPE)?.let {
            binding.includeDescription.includeStatusProgress.progressSpe.progress =
                it.base_stat.toFloat()
            binding.includeDescription.includeStatusProgress.progressSpe.labelText =
                getString(R.string.pokemon_status_progress, it.base_stat)
        }
    }

}

