package com.example.happyplaces.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.happyplaces.databinding.PlacesinfoBinding
import com.example.happyplaces.fragment.HomeDirections
import com.example.happyplaces.models.Destination

class PlacesAdapter:RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder>() {

    private var placelist:List<Destination> = emptyList()

    class PlacesViewHolder(private val placesinfoBinding: PlacesinfoBinding):ViewHolder(placesinfoBinding.root){

        fun bindPlace(destination: Destination){
            placesinfoBinding.apply {
                txtviewdestination.text = destination.city
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacesViewHolder {
        return PlacesViewHolder(PlacesinfoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
       return placelist.size
    }

    override fun onBindViewHolder(holder: PlacesViewHolder, position: Int) {
       val currentPlace = placelist[position]
        holder.bindPlace(currentPlace)

        holder.itemView.setOnClickListener {
           val action =  HomeDirections.actionHomeToDetails(currentPlace)
            it.findNavController().navigate(action)
        }
    }

    fun SetPlaces(places:List<Destination>){
        this.placelist = places
        notifyDataSetChanged()
    }
}