package com.lucasdonato.pokemon_api.presentation.details.adapter

import Types
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasdonato.pokemon_api.R
import com.lucasdonato.pokemon_api.mechanism.extensions.getTypePokemon
import com.lucasdonato.pokemon_api.presentation.base.adapter.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.type_recycler.view.*

class TypeRecyclerAdapter : BaseRecyclerAdapter<Types, TypeRecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(mData[position], position)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(viewGroup.context).inflate(
            (R.layout.type_recycler), viewGroup,
            false
        )
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(types: Types, position: Int) {
            itemView.apply {
                type_recycler_image.setImageResource(getTypePokemon(types.type.name))
            }
        }
    }

}