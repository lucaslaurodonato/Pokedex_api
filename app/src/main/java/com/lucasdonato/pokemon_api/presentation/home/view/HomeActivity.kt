package com.lucasdonato.pokemon_api.presentation.home.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Pokemon
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.mechanism.currency.PaginationListener
import com.lucasdonato.pokemon_api.mechanism.extensions.*
import com.lucasdonato.pokemon_api.mechanism.livedata.Status
import com.lucasdonato.pokemon_api.presentation.AppApplication.Companion.context
import com.lucasdonato.pokemon_api.presentation.details.view.PokemonDetailsActivity
import com.lucasdonato.pokemon_api.presentation.home.adapter.PokemonRecyclerAdapter
import com.lucasdonato.pokemon_api.presentation.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_empty_state.*
import kotlinx.android.synthetic.main.include_search_bar.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    private val presenter: HomePresenter by inject { parametersOf(this) }
    private val adapterList: PokemonRecyclerAdapter by lazy { PokemonRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter.getList()
        setupObserver()
        setupRecyclerView()
        setupSearchPokemon()
    }

    private fun setupObserver() {
        presenter.getListLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> loader.visible()
                Status.SUCCESS -> it.data?.let { data -> setupSuccess(data) }
                Status.ERROR -> setupEmptyState()
                else -> setupEmptyState()
            }
        })
        presenter.searchPokemon.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> loader.visible()
                Status.SUCCESS -> successSearchPokemon(it.data)
                Status.ERROR -> setupSearchError()
                else -> setupSearchError()
            }
        })
    }

    private fun setupRecyclerView() {
        pokemon_recycler.apply {
            adapter = adapterList
            isFocusable = false
            layoutManager = GridLayoutManager(context, 2)
            adapterList.onItemClickListener = {
                startActivity(PokemonDetailsActivity.getStartIntent(context, it, null))
            }
            addOnScrollListener(object :
                PaginationListener(layoutManager as LinearLayoutManager, 20) {
                override fun loadMoreItems() {
                    presenter.limit += 20
                    presenter.getList()
                }

                override val isLoading: Boolean get() = loader.visibility == VISIBLE
            })
        }
    }

    private fun setupSearchPokemon() {
        execute_search_button.setOnClickListener {
            if (search_input_text.validate())
                presenter.getSearchPokemon(search_input_text.get())
        }
    }

    private fun setupSuccess(results: List<Results>) {
        loader.gone()
        results.let {
            showRecyclerBack()
            adapterList.data = it.toMutableList()
        }
    }

    private fun showRecyclerBack() {
        empty_state.gone()
        pokemon_recycler.visible()
        group_home.visible()
    }

    private fun successSearchPokemon(pokemon: Pokemon?) {
        loader.gone()
        startActivity(PokemonDetailsActivity.getStartIntent(this, null, pokemon))
    }

    private fun setupSearchError() {
        loader.gone()
        toast(getString(R.string.home_search_error_text))
    }

    private fun setupEmptyState() {
        loader.gone()
        empty_state.visible()
        pokemon_recycler.gone()
        group_home.gone()
        try_again.setOnClickListener {
            presenter.getList()
        }
    }

}