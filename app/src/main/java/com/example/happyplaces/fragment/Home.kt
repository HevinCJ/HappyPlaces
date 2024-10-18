package com.example.happyplaces.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happyplaces.R
import com.example.happyplaces.viewmodel.HomeViewModel
import com.example.happyplaces.adapter.PlacesAdapter
import com.example.happyplaces.databinding.FragmentHomeBinding
import com.example.happyplaces.result.RetrofitResult

class Home : Fragment() {

  private var home:FragmentHomeBinding?=null
    private val binding get() = home!!

    private val placesadapter: PlacesAdapter by lazy { PlacesAdapter() }

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        home = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRcView()
        LoadDestinations()
        FetchMessageFromServer()
        ActionToAddPlaceFrag()
    }

    private fun ActionToAddPlaceFrag() {
        binding.apply {
            fltactionbtn.setOnClickListener {
                findNavController().navigate(R.id.action_home_to_addPlace)
            }
        }
    }

    private fun FetchMessageFromServer() {
        homeViewModel.LoadMessageFromServer()
        homeViewModel.message.observe(viewLifecycleOwner){result->
            when(result){
                is RetrofitResult.Error ->{

                }
                is RetrofitResult.Loading -> {

                }
                is RetrofitResult.Success -> {
                    binding.apply {
                        txtviewmessage.text = result.data
                    }
                }
            }

        }
    }

    private fun LoadDestinations() {
        homeViewModel.LoadDestinationsFromServer()
        homeViewModel.destinations.observe(viewLifecycleOwner){result->
            when(result){
                is RetrofitResult.Error -> {
                    Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                    Log.d("errormessagretfo",result.message.toString())
                }
                is RetrofitResult.Loading -> {

                }
                is RetrofitResult.Success -> {
                    result.data?.let {
                        placesadapter.SetPlaces(it)
                    }
                }
            }
        }

    }

    private fun setRcView() {
        binding.apply {
            rcdestination.adapter = placesadapter
            rcdestination.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }
    }


}