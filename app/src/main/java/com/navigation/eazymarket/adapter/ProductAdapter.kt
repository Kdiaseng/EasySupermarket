package com.navigation.eazymarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.navigation.eazymarket.R
import com.navigation.eazymarket.model.ProductDTO
import kotlinx.android.synthetic.main.item_product.view.*


class ProductAdapter(
    private val ProductsDTO: List<ProductDTO>,
    val context: Context
) : Adapter<ProductAdapter.ViewHolder>(){

    class  ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bindView(productDTO: ProductDTO){
                itemView.txtNameProduct.text = productDTO.nameProduct
                itemView.txtDescriptionProduct.text = productDTO.descriptionProduct
                itemView.textView_value_product.text = productDTO.valueUnitProduct.toString()
                itemView.texView_quantity_product.text = productDTO.quantityProduct.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(context).inflate(R.layout.item_product,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.ProductsDTO.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productDTO = ProductsDTO[position]
        holder.bindView(productDTO)
    }

}