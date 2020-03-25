package com.navigation.eazymarket.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.navigation.eazymarket.R
import com.navigation.eazymarket.domain.Supermarket
import kotlinx.android.synthetic.main.item_supermarket.view.*


class SupermarketAdapter(
    private val superMarkerts: List<Supermarket>,
    private val context: Context,
    private var itemClickListener: OnSupermarketListener
) : Adapter<SupermarketAdapter.ViewHolder>() {

    var supermarketUsed = getSupermarketIsUsing(superMarkerts)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        fun bindView(
            supermarket: Supermarket,
            action: OnSupermarketListener,
            position: Int,
            supermarketUsed: Supermarket?
        ) {
            val name = itemView.txtNameItem
            val description = itemView.txtDescriptionItem

            name.text = supermarket.name
            description.text = supermarket.description

            supermarketUsed?.let {
                if (supermarket.id != supermarketUsed.id){
                    name.setTextColor(Color.GRAY)
                }
            }

            itemView.setOnLongClickListener {
                action.OnItemLongSupermarket(supermarket, itemView)
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {view ->
                supermarketUsed?.let {
                    if (supermarket.id == supermarketUsed.id ){
                        action.onItemSupermarketClick(supermarket, position,view)
                    }
                } ?: kotlin.run {
                    action.onItemSupermarketClick(supermarket, position, view)
                }

            }

            val iconRemove = itemView.imageRemove

            iconRemove.setOnClickListener {
                action.onClickIconRemove(supermarket)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_supermarket, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return superMarkerts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val superMarketDTO = superMarkerts[position]
        holder.bindView(superMarketDTO, itemClickListener, position, supermarketUsed)
    }

    private fun getSupermarketIsUsing(list: List<Supermarket>): Supermarket?{
        return list.find { it.isUsing }
    }

    interface OnSupermarketListener {
        fun onItemSupermarketClick(supermarket: Supermarket, position: Int, view: View)
        fun OnItemLongSupermarket(supermarket: Supermarket, view: View)
        fun onClickIconRemove(supermarket: Supermarket)

    }


}