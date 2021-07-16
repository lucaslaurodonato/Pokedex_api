package com.lucasdonato.pokemon_api.presentation.details.view

import Types
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.mechanism.EXTRA_ID
import com.lucasdonato.pokemon_api.mechanism.extensions.safeValue
import com.lucasdonato.pokemon_api.mechanism.extensions.toast
import com.lucasdonato.pokemon_api.mechanism.livedata.Status
import com.lucasdonato.pokemon_api.presentation.details.presenter.DetailsPresenter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.include_card_image_description.*
import kotlinx.android.synthetic.main.include_description.*
import kotlinx.android.synthetic.main.include_description.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.round

class PokemonDetailsActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context, pokemon: Results? = null): Intent =
            Intent(context, PokemonDetailsActivity::class.java).apply {
                putExtra(EXTRA_ID, pokemon)
            }
    }

    private val presenter: DetailsPresenter by inject { parametersOf(this) }
    private lateinit var pokemonData: Results

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        receiveData()
        setupObserver()
        clickListeners()
    }

    private fun clickListeners() {
        back.setOnClickListener { finish() }

        var isClick = false

        like_button.setOnClickListener {
            isClick = !isClick

            if (isClick) {
                like_button.background =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_heart)
            } else {
                like_button.background =
                    ContextCompat.getDrawable(applicationContext, R.drawable.ic_disable)
            }
        }
    }

    private fun receiveData() {
        pokemonData = (intent?.getSerializableExtra(EXTRA_ID) as Results?)!!
        pokemonData.number?.let { presenter.getPokemonDetails(it) }
        pokemonData.also {
            it.imageUrl.let { photoUrl ->
                Glide.with(this).load(photoUrl)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?, model: Any?,
                            target: Target<Drawable>?, isFirstResource: Boolean
                        ): Boolean {
                            image_pokemon.setImageResource(R.drawable.ic_close_x)
                            image_progress.visibility = GONE
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?, model: Any?,
                            target: Target<Drawable>?, dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            image_progress.visibility = GONE
                            return false
                        }
                    }).into(image_pokemon)
            }
        }
    }

    private fun setupObserver() {
        presenter.getListLiveData.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    loader.visibility = GONE
                    it.data?.let {
                        name.text = it.name?.capitalize()
                        height.text = getString(R.string.pokemon_height, safeValue(it.height))
                        weight.text = getString(R.string.pokemon_weight, safeValue(it.weight))


                        ///TODO : REFATORAR TUDO ISSO
                        val typeList = mutableListOf<String>()
                        it.types?.map { it -> typeList.addAll(listOf(it.type.name)) }
                        type.text = typeList[0].capitalize()
                        if (it.types?.size!! > 1) {
                            type_two.text = typeList[1].capitalize()
                        } else {
                            type_two.visibility = INVISIBLE
                        }

                        ///TODO : REFATORAR TUDO ISSO
                        val abilitiesList = mutableListOf<String>()
                        it.abilities?.map { it -> abilitiesList.addAll(listOf(it.ability.name)) }
                        abilities_1.text = abilitiesList[0].capitalize()
                        if (it.abilities?.size!! > 1) {
                            abilities_2.text = abilitiesList[1].capitalize()
                        } else {
                            abilities_2.visibility = INVISIBLE
                        }
                    }
                }
                Status.ERROR -> setupErrorToast()
                Status.LOADING -> loader.visibility = VISIBLE
                else -> setupErrorToast()
            }
        })
    }

    private fun setupErrorToast() {
        loader.visibility = GONE
        toast(getString(R.string.error_generic))
    }

}