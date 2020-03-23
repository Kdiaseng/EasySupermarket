package com.navigation.eazymarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
    var isAddInCar: Boolean = false

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

        switch_add_car.setOnCheckedChangeListener { _, isChecked ->
            this.isAddInCar = isChecked
            switch_add_car.text = if (isChecked) "Item adicionado" else "Item não adicionado"
        }

        btnCreateProduct.setOnClickListener {
            val product = Product(
                textInputCode.text.toString(),
                textInputNameProduct.text.toString(),
                textInputDescriptionProduct.text.toString()
            )
            saveProduct(product, textInputValueUnit.text.toString().toDouble())
        }

    }

    private fun saveProduct(product: Product, valueUnit: Double) {
        val idProduct = AppDatabase(activity!!).productDao().add(product)
        val qtd = if (isAddInCar) 1 else 0
        val supermarketProductJoin =
            SupermarketProductJoin(supermarketId, idProduct, valueUnit, qtd)
        saveSupermarketProductJoin(supermarketProductJoin)
        Toast.makeText(activity,"Produto cadastrado com sucesso!!", Toast.LENGTH_SHORT).show()
    }

    private fun saveSupermarketProductJoin(supermarketProductJoin: SupermarketProductJoin) {
        AppDatabase(activity!!).supermarketProductJoinDao().insert(supermarketProductJoin)
    }


}
