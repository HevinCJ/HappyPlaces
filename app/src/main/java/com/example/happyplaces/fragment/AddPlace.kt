package com.example.happyplaces.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.happyplaces.R
import com.example.happyplaces.databinding.FragmentAddPlaceBinding
import com.example.happyplaces.models.Destination
import com.example.happyplaces.result.RetrofitResult
import com.example.happyplaces.viewmodel.AddPlaceViewModel

class AddPlace : Fragment() {
   private var addPlace:FragmentAddPlaceBinding?=null
   private val binding get() = addPlace!!

    private val addPlaceViewModel:AddPlaceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addPlace = FragmentAddPlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewPlaceToServer()
        observeDataAddedToServer()

    }

    private fun addNewPlaceToServer() {
        binding.apply {
           try {
             savebtn.setOnClickListener {
                 val city = edttxtviewcityname.text.toString().trim()
                 val country = edttxtviewcountryname.text.toString().trim()
                 val description = edttxtviewdescription.text.toString().trim()

                 if (city.isNotEmpty() && country.isNotEmpty() && description.isNotEmpty()){
                     val destination = Destination(id = 0,city, description, country)
                     addPlaceViewModel.PostNewPlacesToServer(destination)
                 }else{
                     Toast.makeText(requireContext(),"Please Fill required fields ", Toast.LENGTH_SHORT).show()
                 }
             }
           }catch (E:Exception){
               Log.d("addingexception",E.message.toString())
           }
        }
    }

    private fun observeDataAddedToServer(){
        addPlaceViewModel.newplace.observe(viewLifecycleOwner){result->
            when(result){
                is RetrofitResult.Error -> {

                }
                is RetrofitResult.Loading -> {

                }
                is RetrofitResult.Success -> {
                    findNavController().navigate(R.id.action_addPlace_to_home)
                    Toast.makeText(requireContext(),"Added ${result.data?.city} ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        addPlace = null
    }

}