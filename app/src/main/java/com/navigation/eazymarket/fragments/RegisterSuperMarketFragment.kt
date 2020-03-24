package com.navigation.eazymarket.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Supermarket
import kotlinx.android.synthetic.main.fragment_register_super_market.*
import kotlinx.android.synthetic.main.fragment_register_super_market.view.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterSuperMarketFragment : Fragment() {

    var supermarket: Supermarket? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_register_super_market, container, false)
        view.btnCreateSupermarket.setOnClickListener {
            Navigation.findNavController(it)
                .navigate(R.id.action_registerSuperMarketFragment_to_supermarketFragment2)
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

        if (isSave()) {
            btnCreateSupermarket.text = getString(R.string.SAVE)
            (activity as AppCompatActivity).supportActionBar?.title = "Cadastro"
        } else {
            btnCreateSupermarket.text = getString(R.string.UPDATE)
            (activity as AppCompatActivity).supportActionBar?.title = supermarket!!.name
        }

        btnCreateSupermarket.setOnClickListener {

            val name = textInputName.text.toString().trim()
            val description = textInputdescription.text.toString().trim()

            if (name.isEmpty()) {
                textInputName.error = getString(R.string.NAME_REQUIRED)
                textInputName.requestFocus()
                return@setOnClickListener
            }

            if (description.isEmpty()) {
                textInputdescription.error = getString(R.string.DESCRIPTION_REQUIRED)
                textInputdescription.requestFocus()
                return@setOnClickListener
            }
            val supermarketToSave = Supermarket(name, description)

            if (isSave()) {
                btnCreateSupermarket.text = getString(R.string.SAVE)
                saveSupermarket(supermarketToSave, it)
            } else {
                supermarketToSave.id = this.supermarket!!.id
                updateSupermarket(supermarketToSave)

            }
        }
    }


    private fun isSave(): Boolean {
        return (supermarket == null)
    }

    private fun updateSupermarket(supermarketToSave: Supermarket) {
        AppDatabase(activity!!).supermarketDao().updateSupermarket(supermarketToSave)
        Toast.makeText(activity, getString(R.string.SUCCESS_UPDATE_SUPERMARKET), Toast.LENGTH_SHORT)
            .show()
        Navigation.findNavController(view!!)
            .navigate(R.id.action_registerSuperMarketFragment_to_supermarketFragment2)
    }

    private fun saveSupermarket(supermarket: Supermarket, view: View) {
        class SaveSupermarket : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                AppDatabase(activity!!).supermarketDao().add(supermarket)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(
                    activity,
                    getString(R.string.SUCCESS_CREATE_SUPERMARKET),
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(view)
                    .navigate(R.id.action_registerSuperMarketFragment_to_supermarketFragment2)
            }
        }

        SaveSupermarket().execute()
    }

}
