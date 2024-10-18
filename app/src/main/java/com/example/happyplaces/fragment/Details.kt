package com.example.happyplaces.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.happyplaces.R
import com.example.happyplaces.databinding.FragmentDetailsBinding
import com.example.happyplaces.result.RetrofitResult
import com.example.happyplaces.viewmodel.DetailsViewModel

class Details : Fragment() {
    private var details:FragmentDetailsBinding?=null
    private val binding get() = details!!

    private val detailsArgs by navArgs<DetailsArgs>()

    private val detailsViewModel:DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       details = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadDatatoUi(detailsArgs)
        fetchAndSetDataToUi()
        ActionToUpdateFrag()
        DeleteItemFromServer(detailsArgs)
        observeDeletedData()
    }

    private fun ActionToUpdateFrag() {
        binding.apply {
            updatebtn.setOnClickListener {
                detailsArgs.details.let {
                  val action =   DetailsDirections.actionDetailsToUpdatePlace(it)
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun fetchAndSetDataToUi() {
        detailsViewModel.details.observe(viewLifecycleOwner){details->
            when(details){
                is RetrofitResult.Error -> {
                    Toast.makeText(requireContext(),details.message, Toast.LENGTH_SHORT).show()
                }
                is RetrofitResult.Loading ->{

                }
                is RetrofitResult.Success -> {
                    binding.apply {
                        details.data?.let {
                            txtviewcityname.text = it.city.toString()
                            txtviewcountryname.text = it.country.toString()
                            txtviewdescription.text = it.description.toString()
                            txtviewcitynamefirst.text = it.city.toString()
                        }
                    }
                }
            }
        }
    }

    private fun observeDeletedData() {
        detailsViewModel.deleted.observe(viewLifecycleOwner){deleted->
            when(deleted){
                is RetrofitResult.Error -> {
                    Toast.makeText(requireContext(),deleted.message, Toast.LENGTH_SHORT).show()
                }
                is RetrofitResult.Loading ->{

                }
                is RetrofitResult.Success -> {
                    findNavController().navigate(R.id.action_details_to_home)
                    Toast.makeText(requireContext(),"Destination Deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadDatatoUi(detailsArgs: DetailsArgs) {
      try {
          val id = detailsArgs.details.id
          id.let {
              detailsViewModel.getDestinationDetailsById(it)
          }
      }catch (e:Exception){
          Toast.makeText(requireContext(),e.message, Toast.LENGTH_SHORT).show()
      }
    }

    private fun DeleteItemFromServer(detailsArgs: DetailsArgs){
        binding.apply {
            deletebtn.setOnClickListener {
                try {
                    val id = detailsArgs.details.id
                    id.let {
                        detailsViewModel.deleteItemFromDatabase(it)
                    }

                }catch (e:Exception){

                }
            }
        }
    }


}