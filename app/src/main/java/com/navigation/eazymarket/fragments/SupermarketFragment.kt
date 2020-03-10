package com.navigation.eazymarket.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import com.navigation.eazymarket.R
import com.navigation.eazymarket.adapter.SupermarketAdapter
import com.navigation.eazymarket.dao.SupermarketDao
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.model.SuperMarketDTO
import kotlinx.android.synthetic.main.fragment_supermarket.*
import kotlinx.android.synthetic.main.fragment_supermarket.view.*
import java.lang.Appendable
import java.text.FieldPosition


class SupermarketFragment : Fragment(),  SupermarketAdapter.OnSupermarketListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_supermarket, container, false)

        view.btnRegisterSuperMarket.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_supermarketFragment_to_registerSuperMarketFragment)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recyclerViewMarket = rclistSuperMarket
        recyclerViewMarket.adapter = SupermarketAdapter(genereteListSuperMarket(), this.requireContext(),this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewMarket.layoutManager = layoutManager

    }

    private fun genereteListSuperMarket(): List<SuperMarketDTO> {
        return SuperMarketDTO.converListEntityToListDto(AppDatabase(activity!!).supermarketDao().all())
    }

    override fun onItemSupermarketClick(superMarketDTO: SuperMarketDTO, position: Int) {
            Toast.makeText(context, "muita loucura ${superMarketDTO.name} , position : ${position}",Toast.LENGTH_SHORT).show()
    }

    override fun OnItemLongSupermarket(superMarketDTO: SuperMarketDTO) {
        Toast.makeText(context, "LONG -----: ${superMarketDTO.name}",Toast.LENGTH_SHORT).show()

    }


}