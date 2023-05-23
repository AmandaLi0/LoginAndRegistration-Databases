package com.example.loginandregistration

import com.example.loginandregistration.RegistrationUtil.existingUsers

object RegistrationUtil { // use this in the test class for the is username taken test
    // make another similar list for some taken emails
    var existingUsers = listOf("cosmicF", "cosmicY", "bob", "alice")
//    you can use listOf<type>() instead of making the list & adding individually
//    List<String> blah = new ArrayList<String>();
//    blah.add("hi")
//    blah.add("hello")
//

    // isn't empty
    // already takenA
    // minimum number of characters is 3
    fun validateUsername(username: String) : Boolean {
        if (username.length >=3) {
            if(existingUsers.contains(username)){
                return false
            }
            return true
        }
        return false
    }

    // make sure meets security requirements (deprecated ones that are still used everywhere)
    // min length 8 chars
    // at least one digit
    // at least one capital letter
    // both passwords match
    // not empty
    fun validatePassword(password : String, confirmPassword: String) : Boolean {
        if(password == confirmPassword &&
            password.length>=8 &&
            password.lowercase()!=password) {
                for(x in password){
                    if(x.isDigit()){
                        return true
                    }
                }
        }
        return false
    }
    //            if(password.length >= 8 && password.contains("[A-Z]".toRegex()) && password.contains("[1-9]".toRegex())){
    //                return true
//            }
//            return false
//call.lowercase(), .isDigit()
    // isn't empty
    fun validateName(name: String) : Boolean {
        if(!name.equals(null)){
            return true

        }
        return false
    }

    // isn't empty
    // make sure the email isn't used
    // make sure it's in the proper email format user@domain.tld
    fun validateEmail(email: String) : Boolean {
        if(email == "@domain.tld"){
            return false
        }
        if(email != null){
            var userseg = email.indexOf("@")
            var maybeUser = email.substring(0, userseg)
            if(!(existingUsers.contains(maybeUser))){
                   // existingUsers.add(maybeUser)
                if(email.substring(userseg).contains(".")){
                    return true
                }
            }
        }
        return false
    }
}