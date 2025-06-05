package com.appfoursquare.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appfoursquare.DataUserManager
import com.appfoursquare.adapters.PlaceAdapter
import com.appfoursquare.adapters.PlaceClickChangeEstatusInterface
import com.appfoursquare.adapters.PlaceClickInterface
import com.appfoursquare.databinding.FragmentHomeBinding
import com.appfoursquare.models.UserViewModel
import com.appfoursquare.models.places.Place
import com.appfoursquare.models.places.PlaceViewModel

class HomeFragment : Fragment(), PlaceClickChangeEstatusInterface, PlaceClickInterface {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //de room
    //lateinit var showRV: RecyclerView
    //room users
    private val mainViewModelUser: UserViewModel by viewModels()

    //room places
    private val mainViewModelPlace: PlaceViewModel by viewModels()

    lateinit var placesRV: RecyclerView

    private val placeAdapter = PlaceAdapter(this, this, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        placesRV = binding.ordersRV
        placesRV.layoutManager = LinearLayoutManager(getContext())
        placesRV.adapter = placeAdapter


        initPlaces()
        binding.idFABAddPlace.setOnClickListener {
            moveToAddPlace()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initPlaces(){
        showPlace()
    }

    private fun showPlace(){
        mainViewModelPlace.getPlaces()
        mainViewModelPlace.savedPlaces.observe(viewLifecycleOwner, { placesList ->
            val userType = getUserType()
            if (userType == "admin") {
                binding.idFABAddPlace.visibility = View.INVISIBLE
                placeAdapter.updateList(placesList)
            } else {
                val showPlacesEnabled = ArrayList<Place>()
                for(place in placesList){
                    Log.d("thesearetheplaces","id:"+place.place_id.toString()+"author:"+place.place_author+"status:"+place.place_status)
                    if(place.place_status == "enabled"){
                        showPlacesEnabled += place
                    }
                }
                placeAdapter.updateList(showPlacesEnabled)
            }
        })
    }

    fun getUserType(): String{
        val userType: String = DataUserManager(requireContext()).getPrefUserData()[2]
        return userType
    }

    private fun moveToAddPlace(){
        val navController = findNavController()
        var directions = HomeFragmentDirections.actionNavigationHomeToAddPlaceFragment()
        navController.navigate(directions)
    }

    override fun onChangeEstatusIconClick(place: Place) {
        mainViewModelPlace.editPlace(place.place_id)

        showPlace()

        Toast.makeText(getActivity(), "Plaza habilitada", Toast.LENGTH_LONG).show()
    }

    override fun onPlaceClick(place: Place) {
        var directions = HomeFragmentDirections.actionNavigationHomeToCommentFragment(place)
        NavHostFragment.findNavController(this).navigate(directions)
    }
}