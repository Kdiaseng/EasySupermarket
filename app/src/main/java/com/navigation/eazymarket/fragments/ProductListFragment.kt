package com.navigation.eazymarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    private var productList: List<ProductDTO>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setListProductDTO()
        loadProductsDTO(rcListProduct)

        button_close_car.setOnClickListener {
            productList?.let {
                val idSupermarket = getIdSupermarket(it)
                idSupermarket?.let {
                    setUsingSupermarket(it, false)
                    finishCar(idSupermarket)
                    setListProductDTO()
                    loadProductsDTO(rcListProduct)
                    Toast.makeText(activity, "Lista finalizada com sucesso", Toast.LENGTH_SHORT)
                        .show()
                }
            } ?: kotlin.run {
                Toast.makeText(activity, "Adicione produtos no carrinho", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun finishCar(idSupermarket: Long) {
        AppDatabase(activity!!).supermarketProductJoinDao().finishListCat(idSupermarket)
    }

    private fun loadProductsDTO(rcListProduct: RecyclerView?) {
        productList?.let {
            rcListProduct!!.adapter = ProductAdapter(this.productList!!, this.requireContext(), this)
            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            rcListProduct.layoutManager = layoutManager
            updateFullValue()
        }
    }

    private fun setListProductDTO() {
        this.productList = AppDatabase(activity!!).supermarketProductJoinDao().getAllProductDTO()
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

    private fun updateFullValue() {
        if (!productList.isNullOrEmpty()) {
            txtView_full_value.text = calculateTotalInCar().toString()
        } else {
            txtView_full_value.text = "0.0"
        }
    }

    private fun calculateTotalInCar(): Double {
        val total = this.productList!!.map { item ->
            item.quantityProduct * item.valueUnitProduct
        }
        return total.reduce { sum, item -> sum + item }
    }

    private fun getIdSupermarket(list: List<ProductDTO>): Long? {
        return list[0].supermarketId
    }

    private fun setUsingSupermarket(id: Long, isUsed: Boolean) {
        AppDatabase(activity!!).supermarketDao().setUsingSupermarket(id, isUsed)
    }

}
