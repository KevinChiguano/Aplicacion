package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

//Enviar el listado
class MarvelAdapter(private val items: List<MarvelChars>) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>(){

    //Definir que voy a hacer
    //Trabaja con la vista del metodo de abajo
    class MarvelViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding : MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        //hacer los cambios en esta funcion
        fun render(item : MarvelChars){
            println("Recibiendo a ${item.name}")
            binding.txtName.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)

            binding.imgMarvel.setOnClickListener{
                Snackbar.make(binding.imgMarvel,
                    item.name,
                    Snackbar.LENGTH_SHORT)
                    .show()
            }


        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //Crea una vista
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )

    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position])
    }

    override fun getItemCount(): Int = items.size


}