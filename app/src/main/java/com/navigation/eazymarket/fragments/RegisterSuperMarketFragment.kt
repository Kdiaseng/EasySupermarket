package com.navigation.eazymarket.fragments

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation

import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Supermarket
import kotlinx.android.synthetic.main.fragment_register_super_market.*
import kotlinx.android.synthetic.main.fragment_register_super_market.view.btnCreateSupermarket

/**
 * A simple [Fragment] subclass.
 */
class RegisterSuperMarketFragment : Fragment() {

    var supermarket: Supermarket ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_register_super_market, container, false)
        view.btnCreateSupermarket.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_registerSuperMarketFragment_to_supermarketFragment2)
        }

        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            supermarket = RegisterSuperMarketFragmentArgs.fromBundle(it).supermarket
            textInputName.setText(supermarket?.name)
            textInputdescription.setText(supermarket?.description)
        }

        btnCreateSupermarket.setOnClickListener {
            val name = textInputName.text.toString().trim()
            val description = textInputdescription.text.toString().trim()

            if(name.isEmpty()){
                textInputName.error =getString(R.string.NAME_REQUIRED)
                textInputName.requestFocus()
                return@setOnClickListener
            }

            if(description.isEmpty()){
                textInputdescription.error = getString(R.string.DESCRIPTION_REQUIRED)
                textInputdescription.requestFocus()
                return@setOnClickListener
            }
            val supermarketToSave = Supermarket(name, description)

            if(supermarket == null){
                saveSupermarket(supermarketToSave,it)
            }else {
                supermarketToSave.id = this.supermarket!!.id
                updateSupermarket(supermarketToSave)
            }
        }

}

    private fun updateSupermarket(supermarketToSave: Supermarket) {
            AppDatabase(activity!!).supermarketDao().updateSupermarket(supermarketToSave)
    }

    private fun saveSupermarket(supermarket: Supermarket, view: View){
        class SaveSupermarket: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                AppDatabase(activity!!).supermarketDao().add(supermarket)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(activity, getString(R.string.SUCCESS_CREATE_SUPERMARKET),Toast.LENGTH_SHORT).show()
                Navigation.findNavController(view).navigate(R.id.action_registerSuperMarketFragment_to_supermarketFragment2)
            }
        }

            SaveSupermarket().execute()
    }

}
