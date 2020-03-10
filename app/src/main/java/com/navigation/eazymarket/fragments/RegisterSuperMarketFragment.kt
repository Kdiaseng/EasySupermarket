package com.navigation.eazymarket.fragments

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar

import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Supermarket
import kotlinx.android.synthetic.main.fragment_register_super_market.*
import kotlinx.android.synthetic.main.fragment_register_super_market.view.*
import kotlinx.android.synthetic.main.fragment_register_super_market.view.btnCreateSupermarket
import kotlinx.android.synthetic.main.fragment_supermarket.view.*
import kotlinx.android.synthetic.main.item_supermarket.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterSuperMarketFragment : Fragment() {

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
            val supermarket = Supermarket(name, description)
            saveSupermarket(supermarket,it)
        }
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
