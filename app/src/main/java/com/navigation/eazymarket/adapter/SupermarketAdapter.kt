package com.navigation.eazymarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.navigation.eazymarket.R
import com.navigation.eazymarket.domain.Supermarket
import kotlinx.android.synthetic.main.item_supermarket.view.*


class SupermarketAdapter (
    private val superMarkerts: List<Supermarket>,
    private val context: Context,
    private var itemClickListener: OnSupermarketListener)
    : Adapter<SupermarketAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(supermarket: Supermarket, action: OnSupermarketListener,  position: Int){
            val name = itemView.txtNameItem
            val description = itemView.txtDescriptionItem

            name.text = supermarket.name
            description.text = supermarket.description

            itemView.setOnLongClickListener {
                action.OnItemLongSupermarket(supermarket, itemView)
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
                action.onItemSupermarketClick(supermarket, position, it)
            }

            val iconRemove = itemView.imageRemove

            iconRemove.setOnClickListener {
                action.onClickIconRemove(supermarket)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context) .inflate(R.layout.item_supermarket, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return superMarkerts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superMarketDTO = superMarkerts[position]
        holder.bindView(superMarketDTO, itemClickListener, position)
    }

     interface OnSupermarketListener{
         fun onItemSupermarketClick(supermarket: Supermarket, position: Int, view: View)
         fun OnItemLongSupermarket(supermarket: Supermarket, view: View)
         fun onClickIconRemove(supermarket: Supermarket)

    }



}