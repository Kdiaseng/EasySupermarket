package com.navigation.eazymarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.navigation.eazymarket.R
import com.navigation.eazymarket.adapter.SupermarketAdapter
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.Supermarket
import kotlinx.android.synthetic.main.fragment_supermarket.*
import kotlinx.android.synthetic.main.fragment_supermarket.view.*


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
         loadSupermarketList(rclistSuperMarket)

    }

    private fun loadSupermarketList(recyclerViewMarket: RecyclerView) {
        recyclerViewMarket.adapter =
            SupermarketAdapter(genereteListSuperMarket(), this.requireContext(), this)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerViewMarket.layoutManager = layoutManager
    }

    private fun genereteListSuperMarket(): List<Supermarket> {
        return (AppDatabase(activity!!).supermarketDao().all())
    }

    override fun onItemSupermarketClick(supermarket: Supermarket, position: Int, view: View) {
        val action = SupermarketFragmentDirections.actionSupermarketFragmentToReadQrCodeFragment(supermarket.id)
        Navigation.findNavController(view).navigate(action)
    }

    override fun OnItemLongSupermarket(supermarket: Supermarket, view: View) {
            val action = SupermarketFragmentDirections.actionSupermarketFragmentToRegisterSuperMarketFragment()
            action.supermarket = supermarket
            Navigation.findNavController(view).navigate(action)

    }

    override fun onClickIconRemove(supermarket: Supermarket) {
        val builder = AlertDialog.Builder(activity!!)
        builder.setTitle("Alerta")
        builder.setMessage("Deseja realmente excluir o item?")

        builder.setPositiveButton("SIM"){dialog, which ->
            AppDatabase(activity!!).supermarketDao().delete(supermarket)
            Toast.makeText(context, getString(R.string.REMOVE_ITEM_SUCCESS),Toast.LENGTH_SHORT).show()
            loadSupermarketList(rclistSuperMarket)
        }

        builder.setNegativeButton("NÃO"){dialog,which ->

        }
        val dialog: AlertDialog = builder.create()
        dialog.show()


    }

}
