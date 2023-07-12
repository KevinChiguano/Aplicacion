package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.characters.database.MarvelCharsDB
import com.example.dispositivosmoviles.logic.data.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirstBinding
import com.example.dispositivosmoviles.logic.data.getMarvelCharsDB
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DatailsMarvelItem
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.utilities.DispositivosMoviles
import com.example.dispositivosmoviles.ui.utilities.Metodos
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private lateinit var lManager: LinearLayoutManager

    private lateinit var rvAdapter: MarvelAdapter

    private val limit = 99

    private var offset = 0

    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    private lateinit var gManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentFirstBinding.inflate(
            layoutInflater, container, false
        )

        //da la disposicion de orientacion
        //sabe cuantos elementos han pasado
        lManager = LinearLayoutManager(
            requireActivity(), //contexto -> se pasa el contexto de la activity
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(requireActivity(), 2)

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

//        binding.spinner.adapter = adapter
        //binding.listView.adapter = adapter

        chargeDataRVDBInit(limit, offset)

        binding.rvSwipe.setOnRefreshListener {
            chargeDataRVAPI(offset = offset, limit = limit)
            binding.rvSwipe.isRefreshing = false
            gManager.scrollToPositionWithOffset(5, 20)
        }

        binding.rvMarvelChars.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0) {

                        val v = gManager.childCount
                        val p = gManager.findFirstVisibleItemPosition()
                        val t = gManager.itemCount

                        // v
                        // p es la posicion en la que esta
                        // t es el total de items
                        if ((v + p) >= t) {
                            lifecycleScope.launch(Dispatchers.IO) {
                                //val newItems = JikanAnimeLogic().getAllAnimes()
                                val newItems = MarvelLogic().getAllMarvelChars(offset, limit)

                                withContext(Dispatchers.Main) {
                                    rvAdapter.updateListItem(newItems)
                                    this@FirstFragment.offset += offset
                                }
                            }
                        }
                    }
                }
            }
        )

        //evento para filtrar la informaicon
//        binding.txtFilter.addTextChangedListener {filteredText ->
//            //devuelve una lista
//            val newItems = marvelCharsItems.filter { items ->
//                items.name.lowercase().contains(filteredText.toString().lowercase())
//            }
//            rvAdapter.replaceListAdapter(newItems)
//        }

    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DatailsMarvelItem::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    fun saveMarvelItem(item: MarvelChars):Boolean {
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                DispositivosMoviles
                    .getDbInstance()
                    .marvelDao()
                    .insertMarvelChar(listOf( item.getMarvelCharsDB()))
            }
        }
        return false
    }


    //cambios
    fun chargeDataRVAPI(limit: Int, offset: Int) {

        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharsItems = withContext(Dispatchers.IO) {
                return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))
            }

            rvAdapter = MarvelAdapter(
                marvelCharsItems,
                fnClick = { sendMarvelItem(it) },
                fnSave = {saveMarvelItem(it)}
            )

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }

            this@FirstFragment.offset = offset + limit
        }
    }

    fun chargeDataRVDBInit(limit: Int, offset: Int) {


        if(Metodos().isOnline(requireActivity())){
            lifecycleScope.launch(Dispatchers.Main) {

                marvelCharsItems = withContext(Dispatchers.IO) {

                    return@withContext MarvelLogic().getInitChars(limit, offset)
                }
                rvAdapter = MarvelAdapter(
                    marvelCharsItems,
                    fnClick = { sendMarvelItem(it) },
                    fnSave = {saveMarvelItem(it)}
                )

                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
                this@FirstFragment.offset += limit
            }
        }else{
            Snackbar.make(
                binding.cardView,
                "No hay conexion",
                Snackbar.LENGTH_LONG).show()
        }


    }


}