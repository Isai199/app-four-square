package com.appfoursquare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.appfoursquare.databinding.ActicityLoginBinding
import com.appfoursquare.models.User
import com.appfoursquare.models.UserViewModel
import com.appfoursquare.models.opinions.OpinionViewModel
import com.appfoursquare.models.places.PlaceViewModel

class LoginActivity : AppCompatActivity(){

    private lateinit var binding: ActicityLoginBinding


    //room users
    private val mainViewModelUser: UserViewModel by viewModels()

    // TODO solo se usan para resetear los campos de las tablas de la base de datos
    private val mainViewModelPlace: PlaceViewModel by viewModels()
    private val mainViewModelOpinion: OpinionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActicityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.idBtnLoginIniciarSesion.setOnClickListener {
            initSesion()
        }

        binding.idBtnRegisterRegistrarse.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    fun initSesion() {
        val textLoginCorreo = binding.etLoginCorreo.text.toString()
        val textLoginPassword = binding.etloginPassword.text.toString()

        if(textLoginCorreo.isNotEmpty() && textLoginPassword.isNotEmpty()){
            try {
                mainViewModelUser.getUsers()
                mainViewModelUser.savedUsers.observe(this, { usersList ->
                    for(user in usersList){
                        // NOTE solo para pruebas
                        println("Loginusers: id: ${user.user_id} name: ${user.user_name} email: ${user.user_email} pass: ${user.user_password} type: ${user.user_type}  status: ${user.user_status}")
                        if(user.user_email == textLoginCorreo && user.user_password == textLoginPassword){
                            saveUserInfo(user)
                            userLogged()
                            break
                        }
                    }
                })
            } catch (e: Exception) {
                print("Error: $e")
            }
        }else{
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show()
        }
    }

    fun userLogged(){
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun saveUserInfo(user: User){
        DataUserManager(this).savePrefUserData(user)
    }

    // Eliminar todo el contenido de las tablas
    fun deleteAllDataTables(){
        mainViewModelUser.getUsers()
        mainViewModelUser.savedUsers.observe(this, { usersList ->
            for(user in usersList){
                mainViewModelUser.deleteUser(user)
            }

            if(usersList.isEmpty()){
                println("DELTETE: Se eliminaron todos los usuarios")
            }
        })

        mainViewModelPlace.getPlaces()
        mainViewModelPlace.savedPlaces.observe(this, { placesList ->
            for(place in placesList){
                mainViewModelPlace.deletePlace(place)
            }

            if(placesList.isEmpty()){
                println("DELTETE: Se eliminaron todos los lugares")
            }
        })

        mainViewModelOpinion.getOpinions()
        mainViewModelOpinion.savedOpinions.observe(this, { opinionsList ->
            for(opinion in opinionsList){
                mainViewModelOpinion.deleteOpinion(opinion)
            }

            if(opinionsList.isEmpty()){
                println("DELTETE: Se eliminaron todas las opiniones")
            }
        })
    }


}