package com.appfoursquare

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.appfoursquare.databinding.FragmentAddPlaceBinding
import com.appfoursquare.models.UserViewModel
import com.appfoursquare.models.places.Place
import com.appfoursquare.models.places.PlaceViewModel
import com.squareup.picasso.Picasso
import kotlinx.serialization.descriptors.StructureKind


class AddPlaceFragment : Fragment() {
    private var _binding: FragmentAddPlaceBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //room users
    private val mainViewModelUser: UserViewModel by viewModels()

    //room places
    private val mainViewModelPlace: PlaceViewModel by viewModels()

    // get the arguments from the Registration fragment
    //private val args : AddPlaceFragmentArgs by navArgs()

    private var textAddPlaceDireccion : String = ""
    private var textAddPlaceDescription : String = ""

    private val urlImages: Array<String> = arrayOf(
        "https://st2.depositphotos.com/1053932/5456/i/450/depositphotos_54561555-stock-photo-aguilas-poniente-beach-murcia-in.jpg",
        "https://st.depositphotos.com/1225664/1397/i/450/depositphotos_13976853-stock-photo-europe-famous-places-triumph-arch.jpg",
        "https://st.depositphotos.com/1007970/2030/i/950/depositphotos_20302503-stock-photo-the-sphinx-and-the-great.jpg"
    )
    private var position: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Receive the arguments in a variable
//        val userDetails = args.user
//
//        userDetails?.let {
//            try {
//
//            }catch (e: Exception){
//                e.printStackTrace()
//                Toast.makeText(getActivity(), "Error User", Toast.LENGTH_SHORT).show()
////                var directions = AddPlaceFragmentDirections.actionAddPlaceFragmentToNavigationHome()
////                NavHostFragment.findNavController(this).navigate(directions)
//            }
//        }


        //selecciona ordenamiento
        binding.spAddPlacePhoto.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                var url = getImageUrl(p2)
                val image = binding.ivPlacePhotoDemo
                position = p2
                Picasso.get().load(url).into(image)
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.idBtnAddPlaceCrear.setOnClickListener {
            createPlace()
        }
        binding.idBtnAddPlaceRegresar.setOnClickListener {
            var directions = AddPlaceFragmentDirections.actionAddPlaceFragmentToNavigationHome()
            NavHostFragment.findNavController(this).navigate(directions)
        }
        // Inflate the layout for this fragment
        return root
    }

    private fun getImageUrl(position: Int): String{
        return urlImages[position]
    }

    private fun createPlace(){
        val image: String = urlImages[position].toString()
        textAddPlaceDireccion = binding.etAddPlaceDireccion.text.toString()
        textAddPlaceDescription = binding.etAddPlaceDescripcion.text.toString()

        if(textAddPlaceDireccion.isNotEmpty() && textAddPlaceDescription.isNotEmpty()){
            getUserdata { idUser, nameUser ->
                mainViewModelPlace.savePlace(
                    Place(
                        DataUserManager(requireContext()).getUUID(),
                        image,
                        textAddPlaceDescription,
                        textAddPlaceDireccion,
                        "disabled",
                        nameUser,
                        idUser
                    )
                )
                Toast.makeText(getActivity(), "En espera de ser revisada", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(getActivity(), "Campos incompletos", Toast.LENGTH_LONG).show()
        }
        binding.idBtnAddPlaceCrear.visibility = View.GONE
    }

    private fun getUserdata(onResult: (String, String) -> Unit) {
        val idUser = DataUserManager(requireContext()).getPrefUserData().get(0)
        val nameUser = DataUserManager(requireContext()).getPrefUserData().get(1)
        onResult(idUser, nameUser)
    }
}