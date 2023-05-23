package com.example.loginandregistration

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {
    //methodName_someCondition_expectedResult
    @Test
    fun validatePassword_emptyPassword_isFalse(){
        val actual = RegistrationUtil.validatePassword("", "")
        //asserThat(actualValue).isEqual(desiredValue)
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_passwordsDontMatch_isFalse(){
        val actual = RegistrationUtil.validatePassword("A1fdnuks", "1Afdnuks")
        assertThat(actual).isFalse()

    }
    //make tests for failures of min length of 8 chars, at lest one digit,at least one capital letter
    @Test
    fun validatePassword_passwordsEightChars_isFalse(){
        val actual = RegistrationUtil.validatePassword("A1fdn", "A1fdn")
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_passwordsOneDigit_isFalse(){
        val actual = RegistrationUtil.validatePassword("Afdnaieurfn", "Afdnaieurfn")
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_passwordsOneCapital_isFalse(){
        val actual = RegistrationUtil.validatePassword("fdnaieurfn", "fdnaieurfn")
        assertThat(actual).isFalse()
    }
    @Test
    fun validatePassword_passwordsGood_isTrue(){
        val actual = RegistrationUtil.validatePassword("Gaoundf12", "Gaoundf12")
        assertThat(actual).isTrue()
    }
    //make tests for good matching passwords working

    //make the tests for the other function in the Util class with the common failures and 1 success for each
    @Test
    fun validateEmail_endsInAtdomaintld_isFalse(){
        val actual = RegistrationUtil.validateEmail("@domain.tld")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_incompletedomain_isFalse(){
        val actual = RegistrationUtil.validateEmail("main.tl")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_alreadyExists_isFalse(){
        val actual = RegistrationUtil.validateUsername("bob")
        assertThat(actual).isFalse()
    }

}