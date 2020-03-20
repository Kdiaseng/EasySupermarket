package com.navigation.eazymarket.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Product
import com.navigation.eazymarket.domain.SupermarketProductJoin
import kotlinx.android.synthetic.main.fragment_register_product.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterProductFragment : Fragment() {

    var supermarketId: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_product, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            this.supermarketId = RegisterProductFragmentArgs.fromBundle(it).supermarketId
        }

        btnCreateProduct.setOnClickListener {
            val product = Product(
                textInputCode.text.toString(),
                textInputDescriptionProduct.text.toString(),
                textInputNameProduct.text.toString(),
                textInputValueUnit.text.toString().toDouble()
            )
            saveProduct(product)
        }

    }

    private fun saveProduct(product: Product) {
        val idProduct = AppDatabase(activity!!).productDao().add(product)
        saveSupermarketProductJoin(SupermarketProductJoin(this.supermarketId, idProduct))
    }

    private fun saveSupermarketProductJoin(supermarketProductJoin: SupermarketProductJoin) {
        AppDatabase(activity!!).supermarketProductJoinDao().insert(supermarketProductJoin)
    }

}
