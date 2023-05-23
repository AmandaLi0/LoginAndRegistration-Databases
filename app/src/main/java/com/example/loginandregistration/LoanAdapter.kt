package com.example.loginandregistration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault

class LoanAdapter(var loanList: MutableList<Loan>) :
    RecyclerView.Adapter<LoanAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewAmount : TextView
        val textViewBorrower: TextView
        val layout : ConstraintLayout

        init {
            // Define click listener for the ViewHolder's View
            textViewName = view.findViewById(R.id.textView_itemLoan_name)
            textViewAmount = view.findViewById(R.id.textView_itemLoan_amount)
            textViewBorrower = view.findViewById(R.id.textView_itemLoan_borrower)
            layout = view.findViewById(R.id.ConstraintLayout)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoanAdapter.ViewHolder, position: Int) {
        val loan = loanList[position]
        val context = holder.textViewBorrower.context
        holder.textViewBorrower.text = loan.borrower
        holder.textViewAmount.text = String.format("$%.2f", loan.amount-loan.amountRepaid)
        holder.layout.isLongClickable = true
        holder.layout.setOnLongClickListener {
//            val detailIntent = Intent(it.context, LoanDetailActivity::class.java)
//            detailIntent.putExtra(LoanDetailActivity.EXTRA_LOAN, Loan)
//            it.context.startActivity(detailIntent)

            val popMenu = PopupMenu(context, holder.textViewBorrower)
            popMenu.inflate(R.menu.menu_loan_list_context)
            popMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menu_item_loan_detail_delete -> {
                        deleteFromBackendless(position)
                        true
                    }
                    else -> true
                }
            }
            popMenu.show()
            true
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun deleteFromBackendless(position: Int) {
        val loan = loanList[position]
        Log.d("LoanAdapter", "deleteFromBackendless: Trying to delete ${loanList[position]}")
        // put in the code to delete the item using the callback from Backendless
        // in the handleResponse, we'll need to also delete the item from the loanList
        // and make sure that the recyclerview is updated

        Backendless.Data.of( Loan::class.java).remove(loan, object : AsyncCallback<Long> {
            override fun handleResponse(response: Long?) {
                loanList.removeAt(position)
                notifyDataSetChanged()
            }
            override fun handleFault(fault: BackendlessFault) {
                Log.d("LoanAdapter", "handleFault: ${fault.message}")
            }

        })
    }

    override fun getItemCount(): Int {
        return loanList.size
    }

}