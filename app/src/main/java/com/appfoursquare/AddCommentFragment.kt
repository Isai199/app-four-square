package com.appfoursquare

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.appfoursquare.databinding.FragmentAddCommentBinding
import com.appfoursquare.models.UserViewModel
import com.appfoursquare.models.opinions.Opinion
import com.appfoursquare.models.opinions.OpinionViewModel


class AddCommentFragment : Fragment() {

    private lateinit var binding: FragmentAddCommentBinding

    //room users
    private val mainViewModelUser: UserViewModel by viewModels()
    //room opinions
    private  val mainViewModelOpinion: OpinionViewModel by viewModels()
    //mis variables
    private var idPlace: String = ""
    private var idUser: String = ""

    //args
    private val args : AddCommentFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddCommentBinding.inflate(layoutInflater)

// Receive the arguments in a variable
        val placeDetails = args.place

        placeDetails?.let {
            try {
                idPlace = placeDetails.place_id.toString()
                idUser = getUserId()
                if(idPlace == ""){
                    binding.btnItemAddCommentAddComment.visibility = View.GONE
                    Toast.makeText(getActivity(), "No se puede agregar un comentario, por ahora", Toast.LENGTH_SHORT).show()
                }
            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(getActivity(), "Error User", Toast.LENGTH_SHORT).show()
                var directions = AddCommentFragmentDirections.actionAddCommentFragmentToNavigationHome()
                NavHostFragment.findNavController(this).navigate(directions)
            }
        }

        binding.btnItemAddCommentAddComment.setOnClickListener {
            val textAddCommentOpinion = binding.etItemAddCommentOpinion.text.toString()

            if(textAddCommentOpinion.isNotEmpty() && idPlace != ""){
                if(idUser == ""){
                    Toast.makeText(getActivity(), "Fallo inicio de sesion", Toast.LENGTH_LONG).show()
                } else {
                    mainViewModelOpinion.saveOpinion(Opinion(
                        DataUserManager(requireContext()).getUUID(),
                        textAddCommentOpinion,
                        idUser,
                        idPlace
                    ))
                    Toast.makeText(getActivity(), "Has enviado un comentario", Toast.LENGTH_LONG).show()
                    goBackToHome()
                }
                binding.btnItemAddCommentAddComment.visibility = View.GONE
            }else{

                Toast.makeText(getActivity(), "Campos incompletos", Toast.LENGTH_LONG).show()
            }


        }

        binding.btnItemAddCommentReturn.setOnClickListener {
            goBackToHome()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getUserId(): String {
        val userId: String = DataUserManager(requireContext()).getPrefUserData()[0]
        return userId
    }

    private fun goBackToHome() {
        var directions = AddCommentFragmentDirections.actionAddCommentFragmentToNavigationHome()
        NavHostFragment.findNavController(this).navigate(directions)
    }
}