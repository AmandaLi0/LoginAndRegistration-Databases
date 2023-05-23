package com.example.loginandregistration
import android.os.Parcelable
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Loan (
    var borrower: String = "person",
    var amount: Double = 0.0,
    var reason: String = "food",
    var dateLent: Date = Date(1678726103253),
    var amountRepaid: Double = 0.0,
    var dateComplete: Date? = null,
    var isRepaid: Boolean = false,
    var ownerId: String? = "null",
    var objectId: String? = ""
    ) : Parcelable{
    fun balanceRemaining(): Double{
        return amount - amountRepaid
    }

}