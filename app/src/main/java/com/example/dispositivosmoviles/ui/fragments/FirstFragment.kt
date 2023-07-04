package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivosmoviles.logic.list.ListItems
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DatailsMarvelItem
import com.example.dispositivosmoviles.ui.activities.LoginActivity
import com.example.dispositivosmoviles.ui.activities.MainActivity
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {

    private lateinit var binding : FragmentFirstBinding

    private lateinit var lmanager: LinearLayoutManager

    private lateinit var rvAdapter: MarvelAdapter

    private var page = 1

    private lateinit var marvelCharchItems: MutableList<MarvelChars>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false)

        //da la disposicion de orientacion
        //sabe cuantos elementos han pasado
        lmanager = LinearLayoutManager(
            requireActivity(), //contexto -> se pasa el contexto de la activity
            LinearLayoutManager.VERTICAL,
            false
        )

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

        chargeDataRV("cap")

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV("cap")
            binding.rvSwipe.isRefreshing = false
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {

                        val v = lmanager.childCount
                        val p = lmanager.findFirstVisibleItemPosition()
                        val t = lmanager.itemCount

                        // v
                        // p es la posicion en la que esta
                        // t es el total de items
                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                val newItems = JikanAnimeLogic().getAllAnimes()
                                /*val newItems = MarvelLogic().getMarvelChars(
                                    name = "spi",
                                    limit = 20
                                )
                                */
                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItem(newItems)
                                }
                            }
                        }
                    }
                }
            }
        )

        //evento para filtrar la informaicon
        binding.txtFilter.addTextChangedListener {filteredText ->
            //devuelve una lista
            val newItems = marvelCharchItems.filter { items ->
                items.name.contains(filteredText.toString())
            }
            rvAdapter.replaceListAdapter(newItems)
        }

    }

    //cambiar de activity desde un fragment
    //esta funcion lleva contenido o informacion
    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(), DatailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun corrotine(){
        lifecycleScope.launch(Dispatchers.Main){

            var name = "Kevin"

            name = withContext(Dispatchers.IO){
                //hacer un cambio y retornar solo se puede con uno a la vez
                name = "Paul"
                return@withContext name
            }

            binding.cardView.radius
        }
    }

    //cambios
    fun chargeDataRV(search: String){



        lifecycleScope.launch(Dispatchers.IO) {

            var marvelCharchItems = MarvelLogic().getMarvelChars(
                "spider", page*2
            )

            rvAdapter = MarvelAdapter(
                JikanAnimeLogic().getAllAnimes()
            ){sendMarvelItem(it)}

            withContext(Dispatchers.Main){


                with(binding.rvMarvelChars){
                    val rvMarvel = binding.rvMarvelChars

                    //enlazar el adaptador con el conponente
                    //rvMarvel.adapter = rvAdapter
                    this.adapter = rvAdapter

                    //necesita 3 cosas: un contexto, vista vertical, que se presenten de forma normal o al revez
                    //rvMarvel.layoutManager = LinearLayoutManager
                    this.layoutManager = lmanager
                }

            }
        }
    }



}