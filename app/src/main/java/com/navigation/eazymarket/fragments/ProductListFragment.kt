package com.navigation.eazymarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.navigation.eazymarket.R
import com.navigation.eazymarket.adapter.ProductAdapter
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.model.ProductDTO
import kotlinx.android.synthetic.main.fragment_product_list.*

/**
 * A simple [Fragment] subclass.
 */
class ProductListFragment : Fragment() {

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
        rcListProduct!!.adapter = ProductAdapter(getListProductDTO(), this.requireContext())
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        rcListProduct.layoutManager = layoutManager
    }

    private fun getListProductDTO(): List<ProductDTO> {
        val resutl = AppDatabase(activity!!).supermarketProductJoinDao().getAllProductDTO()
        return resutl
    }

}
