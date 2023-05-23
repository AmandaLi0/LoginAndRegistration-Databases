package com.example.loginandregistration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.loginandregistration.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    companion object {
        val EXTRA_USERNAME = "username"
        val EXTRA_PASSWORD = "password"
        val TAG = "LoginActivity"
        //val EXTRA_USERID = "ownerId"
    }

    val startRegistrationForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                // Handle the Intent
                binding.editTextLoginUsername.setText(intent?.getStringExtra(EXTRA_USERNAME))
                binding.editTextLoginPassword.setText(intent?.getStringExtra(EXTRA_PASSWORD))

            }
        }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)
        setContentView(binding.root)

        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY);

        //logging in to backendless
        binding.buttonLoginLogin.setOnClickListener {
            // do not forget to call Backendless.initApp in the app initialization code

            // do not forget to call Backendless.initApp in the app initialization code
            Backendless.UserService.login(
                binding.editTextLoginUsername.text.toString(),
                binding.editTextLoginPassword.text.toString(),
                object : AsyncCallback<BackendlessUser?> {
                    override fun handleResponse(user: BackendlessUser?) {
                        // user has been logged in
                        Log.d(TAG, "handleResponse: ${user?.getProperty("username")} has logged in")
                        val userId = user!!.objectId
                        retrieveAllData(userId)
                        val loanList = Intent(it.context, LoanListActivity::class.java)
                        loanList.putExtra(LoanListActivity.EXTRA_OWNERID, userId)
                        it.context.startActivity(loanList)

                        finish()
                    }

                    override fun handleFault(fault: BackendlessFault) {
                        // login failed, to get the error code call fault.getCode()
                        Log.d(TAG, "handleFault: ${fault.message}")
                    }
                })


        }

        binding.textViewLoginSignup.setOnClickListener {
            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            registrationIntent.putExtra(
                EXTRA_USERNAME,
                binding.editTextLoginUsername.text.toString()
            )
            registrationIntent.putExtra(
                EXTRA_PASSWORD,
                binding.editTextLoginPassword.text.toString()
            )
            //startActivity(registrationIntent)
            //3b
            startRegistrationForResult.launch(registrationIntent)

        }

    }

    private fun retrieveAllData(userId: String) {
        val whereClause = "ownerId = '$userId'" //userId = objectId of the user
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Loan::class.java).find(queryBuilder, object : AsyncCallback<List<Loan?>?> {
            override fun handleResponse(foundLoans: List<Loan?>?) {
                // first contact instance has been found
                Log.d(TAG, "handleResponse:$foundLoans")
            }

            override fun handleFault(fault: BackendlessFault) {
                Log.d(TAG, "handleResponse:${fault.message}")
            }
        })
    }

}

