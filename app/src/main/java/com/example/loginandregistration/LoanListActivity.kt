package com.example.loginandregistration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.loginandregistration.databinding.ActivityLoanListBinding

class LoanListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoanListBinding
    lateinit var adapter: LoanAdapter

    companion object {
        val EXTRA_OWNERID = "ownerId"
        val EXTRA_USER_ID = "userId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoanListBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val userId = intent.getStringExtra(EXTRA_USER_ID)
        if (userId != null) {
            retrieveAllData(userId)
        }
        binding.fabLoanListCreateNewLoan.setOnClickListener {
            val loanDetailIntent = Intent(this, LoanDetailActivity::class.java).apply { }
            startActivity(loanDetailIntent)
        }
    }

    override fun onStart() {

            super.onStart()
            val userId = intent.getStringExtra(EXTRA_USER_ID)
            if (userId != null) {
                retrieveAllData(userId)
            }

        fun handleFault(fault: BackendlessFault) {
            Log.d(LoginActivity.TAG, "handleResponse:${fault.message}")
        }
    }



    private fun retrieveAllData(userId: String) {
        val whereClause = "ownerId = '$userId'" //userId = objectId of the user
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = whereClause
        Backendless.Data.of(Loan::class.java).find(queryBuilder, object :
            AsyncCallback<List<Loan?>?> {
            override fun handleResponse(foundLoans: List<Loan?>?) {
                // first contact instance has been found
                Log.d(LoginActivity.TAG, "handleResponse:$foundLoans")
                // create the adapter
                adapter = LoanAdapter(foundLoans as MutableList<Loan>)
                // assign it to the recyclerview
                binding.recyclerViewLoanList.adapter = adapter
                binding.recyclerViewLoanList.layoutManager = LinearLayoutManager(this@LoanListActivity)
            }

            override fun handleFault(fault: BackendlessFault) {
                Log.d(LoginActivity.TAG, "handleResponse:${fault.message}")
            }
        })
    }
}