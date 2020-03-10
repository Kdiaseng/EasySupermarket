package com.navigation.eazymarket.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.navigation.eazymarket.R
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            val view: View = inflater.inflate(R.layout.fragment_home, container, false)

//        view.btnNext.setOnClickListener{
//                Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_homeMarket)
//        }
        return view
    }

}
