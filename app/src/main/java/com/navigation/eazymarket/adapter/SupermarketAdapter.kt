package com.navigation.eazymarket.adapter



import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.navigation.eazymarket.R
import com.navigation.eazymarket.model.SuperMarketDTO
import kotlinx.android.synthetic.main.item_supermarket.view.*


class SupermarketAdapter (
    private val superMarkerts: List<SuperMarketDTO>,
    private val context: Context,
    var itemClickListener: OnSupermarketListener)
    : Adapter<SupermarketAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        fun bindView(superMarketDTO: SuperMarketDTO, action: OnSupermarketListener,  position: Int){
            val name = itemView.txtNameItem
            val description = itemView.txtDescriptionItem

            name.text = superMarketDTO.name
            description.text = superMarketDTO.descrition

            itemView.setOnLongClickListener {
                action.OnItemLongSupermarket(superMarketDTO)
                return@setOnLongClickListener true
            }

            itemView.setOnClickListener {
                action.onItemSupermarketClick(superMarketDTO, position)
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
         fun onItemSupermarketClick(superMarketDTO: SuperMarketDTO, position: Int)
         fun OnItemLongSupermarket(superMarketDTO: SuperMarketDTO)
    }



}