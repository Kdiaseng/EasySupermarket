package com.navigation.eazymarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.navigation.eazymarket.R
import com.navigation.eazymarket.adapter.ProductAdapter
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.SupermarketProductJoin
import com.navigation.eazymarket.model.ProductDTO
import kotlinx.android.synthetic.main.fragment_product_list.*
import kotlinx.android.synthetic.main.item_product.*

/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment(), ProductAdapter.OnProductListener {

    var listPrdduct: List<ProductDTO>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadProdutsDTO(rcListProduct)
    }

    private fun loadProdutsDTO(rcListProduct: RecyclerView?) {
        rcListProduct!!.adapter = ProductAdapter(getListProductDTO(), this.requireContext(), this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rcListProduct.layoutManager = layoutManager
        updateFullValue()
    }

    private fun getListProductDTO(): List<ProductDTO> {
        this.listPrdduct = AppDatabase(activity!!).supermarketProductJoinDao().getAllProductDTO()
        return  this.listPrdduct!!
    }

    override fun onClickIncrementProduct(productDTO: ProductDTO) {
        var quantity = productDTO.quantityProduct
        quantity++
        productDTO.quantityProduct = quantity
        updateItemProductDTO(productDTO)
        updateFullValue()
    }

    override fun onClickDecrementProduct(productDTO: ProductDTO) {
        var quantity = productDTO.quantityProduct
        quantity--
        if (quantity >= 0) {
            productDTO.quantityProduct = quantity
            updateItemProductDTO(productDTO)
            updateFullValue()
        }
    }

    private fun updateItemProductDTO(productDTO: ProductDTO) {
        AppDatabase(activity!!).supermarketProductJoinDao()
            .update(
                SupermarketProductJoin(
                    productDTO.supermarketId,
                    productDTO.productId,
                    productDTO.valueUnitProduct,
                    productDTO.quantityProduct
                )
            )
    }

    private fun updateFullValue(){
        if (!listPrdduct.isNullOrEmpty()){
            txtView_full_value.text = calculateTotalInCar().toString()
        }
    }

    private fun calculateTotalInCar(): Double {
        val total = this.listPrdduct!!.map { item ->
            item.quantityProduct * item.valueUnitProduct
        }
      return  total.reduce { sum, item -> sum +item }
    }
}
