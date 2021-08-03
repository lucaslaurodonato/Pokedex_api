package com.lucasdonato.pokemon_api.presentation.home.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.data.model.Results
import com.lucasdonato.pokemon_api.mechanism.currency.PaginationListener
import com.lucasdonato.pokemon_api.mechanism.livedata.Status
import com.lucasdonato.pokemon_api.presentation.details.view.PokemonDetailsActivity
import com.lucasdonato.pokemon_api.presentation.home.adapter.PokemonRecyclerAdapter
import com.lucasdonato.pokemon_api.presentation.home.presenter.HomePresenter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_empty_state.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HomeActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, HomeActivity::class.java)
    }

    private val presenter: HomePresenter by inject { parametersOf(this) }
    private val adapterList: PokemonRecyclerAdapter by lazy { PokemonRecyclerAdapter() }
    private var limitAdd: Int = 20
    private var offSet: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter.getList(limitAdd, offSet)
        setupObserver()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        pokemon_recycler.apply {
            adapter = adapterList
            isFocusable = false

            addOnScrollListener(object :
                PaginationListener(layoutManager as LinearLayoutManager, limitAdd) {
                override fun loadMoreItems() {
                    limitAdd += 20
                    presenter.getList(limitAdd, offSet)
                }

                override val isLoading: Boolean get() = loader.visibility == VISIBLE
            })

            adapterList.onItemClickListener = {
                startActivity(PokemonDetailsActivity.getStartIntent(context, it))
            }
        }
    }

    private fun setupObserver() {
        presenter.getListLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> loader.visibility = VISIBLE
                Status.SUCCESS -> it.data?.let { data -> setupSuccess(data) }
                Status.ERROR -> setupEmptyState()
                else -> setupEmptyState()
            }
        })
    }

    private fun setupSuccess(results: List<Results>) {
        loader.visibility = GONE
        results.let {
            showRecyclerBack()
            adapterList.data = it.toMutableList()
        }
    }

    private fun showRecyclerBack() {
        empty_state.visibility = GONE
        pokemon_recycler.visibility = VISIBLE
        group_home.visibility = VISIBLE
    }

    private fun setupEmptyState() {
        loader.visibility = GONE

        empty_state.visibility = VISIBLE
        pokemon_recycler.visibility = GONE
        group_home.visibility = GONE

        try_again.setOnClickListener {
            presenter.getList(limitAdd, offSet)
        }
    }

}