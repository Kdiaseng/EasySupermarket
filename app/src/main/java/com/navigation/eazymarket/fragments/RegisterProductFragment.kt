package com.navigation.eazymarket.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Product
import com.navigation.eazymarket.domain.SupermarketProductJoin
import com.navigation.eazymarket.model.RegisterProductParam
import kotlinx.android.synthetic.main.fragment_register_product.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterProductFragment : Fragment() {

    var registerProductParam: RegisterProductParam? = null
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
            this.registerProductParam = RegisterProductFragmentArgs.fromBundle(it).registerProductParam
        }

        textInputCode.setText(registerProductParam!!.code)
        switch_add_car.setOnCheckedChangeListener { _, isChecked ->
            this.isAddInCar = isChecked
            switch_add_car.text = if (isChecked) "Item adicionado  " else "Item não adicionado  "
        }

        btnCreateProduct.setOnClickListener {

            if (validateFieldEmpty()) return@setOnClickListener

            val product = Product(
                textInputCode.text.toString(),
                textInputNameProduct.text.toString(),
                textInputDescriptionProduct.text.toString()
            )
            saveProduct(product, textInputValueUnit.text.toString().toDouble())
        }

    }

    private fun validateFieldEmpty(): Boolean {
        if (textInputNameProduct.text.toString().isEmpty()) {
            textInputNameProduct.error = getString(R.string.NAME_REQUIRED)
            textInputNameProduct.requestFocus()
            return true
        }

        if (textInputDescriptionProduct.text.toString().isEmpty()) {
            textInputDescriptionProduct.error = getString(R.string.DESCRIPTION_REQUIRED)
            textInputDescriptionProduct.requestFocus()
            return true
        }

        if (textInputValueUnit.text.toString().isEmpty()) {
            textInputValueUnit.error = getString(R.string.VALUE_UNIT_REQUIRED)
            textInputValueUnit.requestFocus()
            return true
        }
        return false
    }

    private fun saveProduct(product: Product, valueUnit: Double) {
        val idProduct = AppDatabase(activity!!).productDao().add(product)
        val qtd = if (isAddInCar) 1 else 0
        val supermarketProductJoin =
            SupermarketProductJoin(registerProductParam!!.supermarketId, idProduct, valueUnit, qtd)
        saveSupermarketProductJoin(supermarketProductJoin)
        if (isAddInCar)
            setUsingSupermarket(registerProductParam!!.supermarketId, true)
        Toast.makeText(activity,"Produto cadastrado com sucesso!!", Toast.LENGTH_SHORT).show()

        val action = RegisterProductFragmentDirections.actionRegisterProductFragmentToReadQrCodeFragment(this.registerProductParam!!.supermarketId)
        Navigation.findNavController(this.view!!).navigate(action)
    }

    private fun saveSupermarketProductJoin(supermarketProductJoin: SupermarketProductJoin) {
        AppDatabase(activity!!).supermarketProductJoinDao().insert(supermarketProductJoin)
    }

    private fun setUsingSupermarket(id: Long, isUsed: Boolean){
         AppDatabase(activity!!).supermarketDao().setUsingSupermarket(id, isUsed)
    }


}
