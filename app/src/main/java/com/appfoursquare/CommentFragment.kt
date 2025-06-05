package com.appfoursquare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appfoursquare.adapters.CommentAdapter
import com.appfoursquare.databinding.FragmentCommentBinding
import com.appfoursquare.models.UserViewModel
import com.appfoursquare.models.opinions.OpinionViewModel
import com.appfoursquare.models.opinions.OpinionWithUser
import com.appfoursquare.models.places.Place
import com.squareup.picasso.Picasso
import java.util.ArrayList

// TODO mostrar el lugar publicado por el usuario arriba mas "resaltado", y abajo mostrar todos los comentarios
class CommentFragment : Fragment() {

    private var _binding: FragmentCommentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //args
    private val args : CommentFragmentArgs by navArgs()

    private val mainViewModelOpinion: OpinionViewModel by viewModels()

    private val mainViewModelUser: UserViewModel by viewModels()

    lateinit var commentsRV: RecyclerView

    private val commentAdapter = CommentAdapter(this)

    //mis variables
    private var idPlace = ""
    private  var placePhoto = ""
    private var placeDescription = ""
    private var placeTitle = ""
    private var placeStatus = ""
    private var placeAuthor = ""
    private var placeIdUser = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        commentsRV = binding.opinionsRV
        commentsRV.layoutManager = LinearLayoutManager(getContext())
        commentsRV.adapter = commentAdapter

        // Receive the arguments in a variable
        val placeDetails = args.place

        placeDetails?.let {

            try {

                // TODO cambiar el nombre de la variable placeAdress a placeTitle
                idPlace = placeDetails.place_id.toString()
                placeDescription = placeDetails.place_description
                placeTitle = placeDetails.place_title
                placeStatus = placeDetails.place_status
                placeAuthor = placeDetails.place_author
                placeIdUser = placeDetails.place_iduser
                placePhoto = placeDetails.place_photo

                if(idPlace != ""){
                    showPlace(placeAuthor, placeDescription, placePhoto)
                    showComment()
                }
            } catch (e: Exception) {
                var directions = CommentFragmentDirections.actionCommentFragmentToNavigationHome()
                NavHostFragment.findNavController(this).navigate(directions)
            }

        }


        binding.idFABAddComment.setOnClickListener {
            if(idPlace != "" && placeDescription != "" && placeTitle != "" && placeStatus != "" && placeAuthor != "" && placeIdUser != "" && placePhoto != ""){
               val place = Place(
                   idPlace,
                   placePhoto,
                   placeDescription,
                   placeTitle,
                   placeStatus,
                   placeAuthor,
                   placeIdUser
               )

                goToAddComment(place)
            }else{
                Toast.makeText(getActivity(), "Error de solicitud", Toast.LENGTH_LONG).show()
            }
        }

        binding.idFABACommnetReturnt.setOnClickListener {
            var directions = CommentFragmentDirections.actionCommentFragmentToNavigationHome()
            NavHostFragment.findNavController(this).navigate(directions)
        }

        // Inflate the layout for this fragment
        return root
    }

    private fun showComment(){
        mainViewModelOpinion.getOpinionAndUser(idPlace)
        mainViewModelOpinion.savedOpinionAndUser.observe(viewLifecycleOwner, { opinionAndUser ->
            var allComments: ArrayList<OpinionWithUser> = arrayListOf()
            for(comment in opinionAndUser){
                allComments += comment
            }
            commentAdapter.updateList(allComments)
        })
    }

    private fun showPlace(placeAuthor: String, placeDescription: String, placePhoto: String){
        binding.tvAuthorPlace.setText("Autor: $placeAuthor")
        binding.tvAuthorDescription.setText(placeDescription)
        val image = binding.ivCommentedPlace
        val dir = placePhoto
        Picasso.get().load(dir).into(image)
    }

    private fun goToAddComment(place: Place) {
        var directions = CommentFragmentDirections.actionCommentFragmentToAddCommentFragment(place)
        NavHostFragment.findNavController(this).navigate(directions)
    }
}