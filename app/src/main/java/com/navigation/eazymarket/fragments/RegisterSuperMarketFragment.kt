package com.navigation.eazymarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.navigation.eazymarket.R
import kotlinx.android.synthetic.main.fragment_register_super_market.view.*
import kotlinx.android.synthetic.main.fragment_supermarket.view.*

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

}
