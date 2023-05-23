package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.loginandregistration.databinding.ActivityRegistrationBinding


class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val username = intent.getStringExtra(LoginActivity.EXTRA_USERNAME) ?: ""
        val password = intent.getStringExtra(LoginActivity.EXTRA_PASSWORD) ?: ""

        binding.editTextRegistrationUsername.setText(username)
        binding.editTextRegistrationPassword.setText(password)




        //register an account and send back the username and password to the login activity to prefill those fields
        binding.buttonRegistrationRegister.setOnClickListener {
            val password = binding.editTextRegistrationPassword.text.toString()
            val confirm = binding.editTextRegistrationConfirmPassword.text.toString()
            val username = binding.editTextRegistrationUsername.text.toString()
            val name = binding.editTextRegistrationName.text.toString()
            val email = binding.editTextTextEmailAddress.text.toString()
            val user = BackendlessUser()
            user.setProperty("email", email)
            user.setProperty("username", username)
            user.setProperty("name", name)
            user.setProperty("password", password)

           Backendless.UserService.register(user, object: AsyncCallback<BackendlessUser?>{
               override fun handleResponse(registeredUser: BackendlessUser?) {
                   Toast.makeText(this@RegistrationActivity, "you have been registered and can log in!", Toast.LENGTH_SHORT).show()
               }

               override fun handleFault(fault: BackendlessFault) {
                   Toast.makeText(this@RegistrationActivity, "you haven't been registered and cannot log in!", Toast.LENGTH_SHORT).show()
               }
           })

           }




//            if(RegistrationUtil.validatePassword(password, confirm) &&
//                RegistrationUtil.validateUsername(username) &&
//                RegistrationUtil.validateName(name) &&
//                RegistrationUtil.validateEmail(email)){
//                //apply lambda will call the functions inside it on the object that apply is called on
//                //apply {putExtra90} is doing the same as resultIntent.putExtra()
//                val resultIntent = Intent().apply {
//                    putExtra(LoginActivity.EXTRA_USERNAME, binding.editTextRegistrationUsername.text.toString())
//                    putExtra(LoginActivity.EXTRA_PASSWORD, binding.editTextRegistrationPassword.text.toString())
//
//                }
//                setResult(Activity.RESULT_OK, resultIntent)
//                finish()
//
//
//            }
//            else{
//                Toast.makeText(this, "Please fill out all fields correctly!", Toast.LENGTH_SHORT).show()
//            }
//            //apply lambda will call the functions inside it on the object that apply is called on
//            //apply {putExtra90} is doing the same as resultIntenet.putExtra()

        }

        //RegistrationUtil.validatePassword(password, confirm) &&
        //                RegistrationUtil.validateUsername(username) &&
        //                RegistrationUtil.validateName(name) &&
        //                RegistrationUtil.validateEmail(email)

    }
