package com.navigation.eazymarket.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.navigation.eazymarket.R
import com.navigation.eazymarket.model.ProductDTO
import kotlinx.android.synthetic.main.item_product.view.*


class ProductAdapter(
    private val ProductsDTO: List<ProductDTO>,
    val context: Context,
    private var itemClickListener: OnProductListener
) : Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(productDTO: ProductDTO, action: OnProductListener) {
            itemView.txtNameProduct.text = productDTO.nameProduct
            itemView.txtDescriptionProduct.text = productDTO.descriptionProduct
            itemView.textView_value_product.text = productDTO.valueUnitProduct.toString()
            itemView.texView_quantity_product.text = productDTO.quantityProduct.toString()


            itemView.button_add_quantity.setOnClickListener {
                if (productDTO.quantityProduct == 0) {
                    itemView.txtNameProduct.paintFlags = 0
                }
                action.onClickIncrementProduct(productDTO)
                itemView.texView_quantity_product.text = productDTO.quantityProduct.toString()

            }

            itemView.button_remove_quantity.setOnClickListener {
                action.onClickDecrementProduct(productDTO)
                itemView.texView_quantity_product.text = productDTO.quantityProduct.toString()
                if (productDTO.quantityProduct <= 0.0) {
                    itemView.txtNameProduct.paintFlags =
                        itemView.txtNameProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this.ProductsDTO.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productDTO = ProductsDTO[position]
        holder.bindView(productDTO, itemClickListener)
    }

    interface OnProductListener {
        fun onClickIncrementProduct(productDTO: ProductDTO)
        fun onClickDecrementProduct(productDTO: ProductDTO)
    }

}