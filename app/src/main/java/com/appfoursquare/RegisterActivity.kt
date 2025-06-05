package com.appfoursquare

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.appfoursquare.databinding.ActivityRegisterBinding
import com.appfoursquare.models.User
import com.appfoursquare.models.UserViewModel

class RegisterActivity : AppCompatActivity(){

    private lateinit var binding: ActivityRegisterBinding

    //room users
    private val mainViewModelUser: UserViewModel by viewModels()

    private var registerName: String = ""
    private var RegisterMail: String = ""
    private var registerPassword: String = ""
    private var typeAcount: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.idBtnRegisterRegistrarse.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser(){
        registerName = binding.etRegisterNombre.text.toString()
        RegisterMail =binding.etRegisterCorreo.text.toString()
        registerPassword =binding.etRegisterPassword.text.toString()
        typeAcount = binding.spRegisterType.selectedItem.toString()

        if(registerName.isNotEmpty() && RegisterMail.isNotEmpty() && registerPassword.isNotEmpty()){
            addUser()
            accountCreated()
        } else {
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show()
        }
    }

    private fun addUser() {
        mainViewModelUser.saveUser(
            User(DataUserManager(this).getUUID(),
                registerName,
                RegisterMail,
                registerPassword,
                typeAcount,
                "disconnected" // TODO ver si eliminar la opcion del estatus
            )
        )
    }

    private fun accountCreated() {
        Toast.makeText(this, "Cuenta creada", Toast.LENGTH_LONG).show()
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        this.finish()

    }
}