package com.navigation.eazymarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.navigation.eazymarket.R
import com.navigation.eazymarket.adapter.SupermarketAdapter
import com.navigation.eazymarket.model.SuperMarketDTO
import kotlinx.android.synthetic.main.fragment_supermarket.*
import kotlinx.android.synthetic.main.fragment_supermarket.view.*


class SupermarketFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_supermarket, container, false)

        view.btnRegisterSuperMarket.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_supermarketFragment_to_registerSuperMarketFragment)
        }

        val recyclerViewMaktet = view.rclistSuperMarket
        recyclerViewMaktet.adapter = SupermarketAdapter(genereteListSuperMarket(), this.requireContext())
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewMaktet.layoutManager = layoutManager

        return view
    }

    private fun genereteListSuperMarket(): List<SuperMarketDTO> {
            return listOf(
                SuperMarketDTO("DB Supermercados", "AV.TORQUATO"),
                SuperMarketDTO("Atacadão", "AV.ALTAZ MIRIM"),
                SuperMarketDTO("BARATÃO DA CARNE", "AV.TORQUATO"),
                SuperMarketDTO("NOVA ERA", "AV.TORQUATO"),
                SuperMarketDTO("AÇAI ATACADISTA", "AV.TORQUATO"),
                SuperMarketDTO("SUPERMERCADOS RODRIGUES", "AV.TORQUATO"),
                SuperMarketDTO("PARQUE GURMET", "AV.TORQUATO")

                )
    }


}
