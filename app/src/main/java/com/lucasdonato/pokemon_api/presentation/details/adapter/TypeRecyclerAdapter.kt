package com.lucasdonato.pokemon_api.presentation.details.adapter

import Types
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.databinding.TypeRecyclerBinding
import com.lucasdonato.pokemon_api.mechanism.extensions.getTypePokemon
import com.lucasdonato.pokemon_api.presentation.base.adapter.BaseRecyclerAdapter

class TypeRecyclerAdapter : BaseRecyclerAdapter<Types, TypeRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mData[position], position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.context),
                R.layout.type_recycler,
                viewGroup,
                false
            )
        )
    }

    inner class ViewHolder(binding: TypeRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val typePokemon = binding.typeRecyclerImage

        fun bind(types: Types, position: Int) {
            typePokemon.setImageResource(getTypePokemon(types.type.name))
        }
    }

}