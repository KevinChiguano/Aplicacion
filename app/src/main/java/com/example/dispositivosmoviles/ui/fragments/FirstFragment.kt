package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivosmoviles.logic.list.ListItems
import com.example.dispositivosmoviles.ui.activities.DatailsMarvelItem
import com.example.dispositivosmoviles.ui.activities.LoginActivity
import com.example.dispositivosmoviles.ui.activities.MainActivity
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        val names = arrayListOf<String>("Carlos", "Xavier", "Andrés", "Pepe", "Mariano", "Rosa")

        val adapter = ArrayAdapter<String>(
            requireActivity(),
            R.layout.simple_layout,
            names
        )

        binding.spinner.adapter = adapter
        //binding.listView.adapter = adapter

        chargeDataRV()

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }


    }

    //cambiar de activity desde un fragment
    //esta funcion lleva contenido o informacion
    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(), DatailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun chargeDataRV(){

        lifecycleScope.launch(Dispatchers.IO) {
            val rvAdapter = MarvelAdapter(
                //ListItems().returnMarvelChars()
                JikanAnimeLogic().getAllAnimes(),
            ) {sendMarvelItem(it)}

            withContext(Dispatchers.Main){
                with(binding.rvMarvelChars){
                    val rvMarvel = binding.rvMarvelChars

                    //enlazar el adaptador con el conponente
                    //rvMarvel.adapter = rvAdapter
                    this.adapter = rvAdapter

                    //necesita 3 cosas: un contexto, vista vertical, que se presenten de forma normal o al revez
                    //rvMarvel.layoutManager = LinearLayoutManager
                    this.layoutManager = LinearLayoutManager(
                        requireActivity(), //contexto -> se pasa el contexto de la activity
                        LinearLayoutManager.VERTICAL,
                        false)
                }

            }
        }
    }



}