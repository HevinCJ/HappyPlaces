package com.example.happyplaces.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.happyplaces.R
import com.example.happyplaces.databinding.FragmentUpdatePlaceBinding
import com.example.happyplaces.models.Destination
import com.example.happyplaces.result.RetrofitResult
import com.example.happyplaces.viewmodel.UpdatePlaceViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class UpdatePlace : Fragment() {
    private var updatePlace:FragmentUpdatePlaceBinding?=null
    private val binding get() = updatePlace!!

    private val updatePlaceArgs:UpdatePlaceArgs by navArgs()

    private val updatePlaceViewModel:UpdatePlaceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       updatePlace = FragmentUpdatePlaceBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArgsIntoView()
        updateDataToServer()
        observeUpdateToServer()
    }

    private fun observeUpdateToServer() {
        updatePlaceViewModel.updates.observe(viewLifecycleOwner){
            when(it){
                is RetrofitResult.Error -> {
                    Toast.makeText(requireContext(),it.message, Toast.LENGTH_SHORT).show()
                }
                is RetrofitResult.Loading ->{

                }
                is RetrofitResult.Success -> {
                    Toast.makeText(requireContext(),"Updated Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_updatePlace_to_home)
                }
            }
        }
    }

    private fun updateDataToServer() {
      binding.savebtn.setOnClickListener {
          lifecycleScope.launch {
              try {
                  binding.apply {

                      val id  = updatePlaceArgs.updateArgs.id
                      val city =  edttxtviewcityname.text?.toString()?.trim()
                      val country =  edttxtviewcountryname.text?.toString()?.trim()
                      val description =  edttxtviewdescription.text?.toString()?.trim()

                      val destination = Destination(id,city,description,country)
                      updatePlaceViewModel.updateDataInServer(destination)


                  }
              }catch (e:Exception){

              }
          }
      }
    }

    private fun setArgsIntoView() {
        binding.apply {
            if (updatePlaceArgs.updateArgs.toString().isNotEmpty()){
                edttxtviewcityname.setText(updatePlaceArgs.updateArgs.city)
                edttxtviewcountryname.setText(updatePlaceArgs.updateArgs.country)
                edttxtviewdescription.setText(updatePlaceArgs.updateArgs.description)
            }
        }

    }

}