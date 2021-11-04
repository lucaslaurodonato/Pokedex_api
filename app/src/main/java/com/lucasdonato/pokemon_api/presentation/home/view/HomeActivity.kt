package com.lucasdonato.pokemon_api.presentation.home.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.databinding.ActivityHomeBinding
import com.lucasdonato.pokemon_api.mechanism.QUANTITY
import com.lucasdonato.pokemon_api.mechanism.currency.PaginationListener
import com.lucasdonato.pokemon_api.mechanism.extensions.*
import com.lucasdonato.pokemon_api.mechanism.livedata.Status
import com.lucasdonato.pokemon_api.presentation.base.view.BaseActivity
import com.lucasdonato.pokemon_api.presentation.details.view.PokemonDetailsActivity
import com.lucasdonato.pokemon_api.presentation.home.adapter.PokemonRecyclerAdapter
import com.lucasdonato.pokemon_api.presentation.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_empty_state.*
import kotlinx.android.synthetic.main.include_empty_state.view.*
import kotlinx.android.synthetic.main.include_search_bar.*
import kotlinx.android.synthetic.main.include_search_bar.view.*
import org.koin.android.ext.android.bind
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    private val presenter: HomePresenter by inject()
    private val adapterList: PokemonRecyclerAdapter by lazy { PokemonRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getList()
        setupObserver()
        setupRecyclerView()
        setupSearchPokemon()
    }

    private fun setupObserver() {
        presenter.getListLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> binding.loader.visible()
                Status.SUCCESS -> it.data?.let { data -> setupSuccess(data) }
                Status.ERROR -> setupEmptyState()
                else -> setupEmptyState()
            }
        })
        presenter.searchPokemon.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> binding.loader.visible()
                Status.SUCCESS -> successSearchPokemon(it.data)
                Status.ERROR -> setupSearchError()
                else -> setupSearchError()
            }
        })
    }

    private fun setupRecyclerView() {
        binding.pokemonRecycler.apply {
            adapter = adapterList
            adapterList.onItemClickListener = {
                startActivity(PokemonDetailsActivity.getStartIntent(context, it, null))
            }
            addOnScrollListener(object :
                PaginationListener(layoutManager as LinearLayoutManager, QUANTITY) {
                override fun loadMoreItems() {
                    presenter.limit += QUANTITY
                    presenter.getList()
                }

                override val isLoading: Boolean get() = loader.visibility == VISIBLE
            })

        }
    }

    private fun setupSearchPokemon() {
        binding.searchToolbar.execute_search_button.setOnClickListener {
            if (search_input_text.validate())
                presenter.getSearchPokemon(search_input_text.get())
        }
    }

    private fun setupSuccess(results: List<Results>?) {
        binding.loader.gone()
        results?.let {
            showRecyclerBack()
            adapterList.data = it.toMutableList()
        }
    }

    private fun showRecyclerBack() {
        binding.emptyState.gone()
        binding.pokemonRecycler.visible()
        binding.groupHome.visible()
    }

    private fun successSearchPokemon(pokemon: Pokemon?) {
        binding.loader.gone()
        startActivity(PokemonDetailsActivity.getStartIntent(this, null, pokemon))
    }

    private fun setupSearchError() {
        binding.loader.gone()
        toast(getString(R.string.home_search_error_text))
    }

    private fun setupEmptyState() {
        binding.emptyState.visible()
        binding.loader.gone()
        binding.pokemonRecycler.gone()
        binding.groupHome.gone()
        binding.emptyState.try_again.setOnClickListener {
            presenter.getList()
        }
    }

}