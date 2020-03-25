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

/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment(), ProductAdapter.OnProductListener {

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
    }

    private fun getListProductDTO(): List<ProductDTO> {
        return AppDatabase(activity!!).supermarketProductJoinDao().getAllProductDTO()
    }

    override fun onClickIncrementProduct(productDTO: ProductDTO) {
        var quantity = productDTO.quantityProduct
        quantity++
        productDTO.quantityProduct = quantity
        updateItemProductDTO(productDTO)
    }

    override fun onClickDecrementProduct(productDTO: ProductDTO) {
        var quantity = productDTO.quantityProduct
        quantity--
        if (quantity > 0) {
            productDTO.quantityProduct = productDTO.quantityProduct--
            updateItemProductDTO(productDTO)
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


}
