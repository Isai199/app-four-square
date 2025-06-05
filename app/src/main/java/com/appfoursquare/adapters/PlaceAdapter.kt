package com.appfoursquare.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.appfoursquare.R
import com.appfoursquare.models.places.Place
import com.appfoursquare.ui.home.HomeFragment
import com.squareup.picasso.Picasso

class PlaceAdapter(
    val context: HomeFragment,
    val placeClickChangeEstatusInterface: PlaceClickChangeEstatusInterface,
    val placeClickInterface: PlaceClickInterface
):
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    // on below line we are creating a
    // variable for our all places list.
    private val allPlaces = ArrayList<Place>()


    // on below line we are creating a view holder class.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTV = itemView.findViewById<TextView>(R.id.tvPlacetitle)
        val descriptionTV = itemView.findViewById<TextView>(R.id.tvDescription)
        val enabledBtn = itemView.findViewById<Button>(R.id.idBtnSendEnviar)
        val addcommentBtn = itemView.findViewById<Button>(R.id.btnAddComment)
        val photoIV = itemView.findViewById<ImageView>(R.id.ivPlace)
        val authorTV = itemView.findViewById<TextView>(R.id.tvAuthor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflating our layout file for each item of recycler view.
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_place,
            parent, false
        )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // on below line we are setting data to item of recycler view.
        holder.titleTV.setText(allPlaces.get(position).place_title.uppercase())
        holder.descriptionTV.setText("Descripcion : " + allPlaces.get(position).place_description)
        holder.authorTV.setText("Autor: "+ allPlaces.get(position).place_author)

        val image = holder.photoIV
        val dir = allPlaces.get(position).place_photo
        Picasso.get().load(dir).into(image)

        //boton habilitar, solo para el admin
        if(allPlaces.get(position).place_status == "enabled"){
            holder.enabledBtn.visibility = View.GONE
        }
        // TODO desabilitar la opcion de comentar o cambiarle la descripcion al boton de comentar, para el admin
        if(allPlaces.get(position).place_status == "disabled"){
            holder.addcommentBtn.visibility = View.GONE
        }

        //cambiar status
        holder.enabledBtn.setOnClickListener {

            placeClickChangeEstatusInterface.onChangeEstatusIconClick(allPlaces.get(position))
            holder.enabledBtn.visibility = View.GONE
        }

        //ver comentarios
        holder.addcommentBtn.setOnClickListener {
            placeClickInterface.onPlaceClick(allPlaces.get(position))
        }
    }

    override fun getItemCount(): Int {
        // on below line we are
        // returning our list size.
        return allPlaces.size
    }

    // below method is use to update our list of places.
    fun updateList(newList: List<Place>) {
        // on below line we are clearing
        // our places array list
        allPlaces.clear()
        // on below line we are adding a
        // new list to our all places list.
        allPlaces.addAll(newList)
        // on below line we are calling notify data
        // change method to notify our adapter.
        notifyDataSetChanged()
    }
}

interface PlaceClickChangeEstatusInterface {
    // creating a method for click
    // action on delete image view.
    fun onChangeEstatusIconClick(place: Place)
}

interface PlaceClickInterface {
    // creating a method for click action
    // on recycler view item for updating it.
    fun onPlaceClick(place: Place)
}