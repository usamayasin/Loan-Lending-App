package org.jethro.mobile.models.register

import com.google.gson.annotations.SerializedName

/**
 * Created by dilpreet on 31/7/17.
 */

data class RegisterPayload (

    @SerializedName("username")
    var username: String? = null,

    @SerializedName("firstname")
    var firstname: String? = null,

    @SerializedName("lastname")
    var lastname: String? = null,

    @SerializedName("officeId")
    var officeId: Int? = null,

    @SerializedName("roles")
    var roles: Array<Int> ?= null,

    @SerializedName("isSelfServiceUser")
    var isSelfServiceUser: Boolean? = null,

    @SerializedName("clients")
    var clients: Array<Int> ?=  null,

    @SerializedName("sendPasswordToEmail")
    var sendPasswordToEmail: Boolean? = null,

    @SerializedName("password")
    var password: String? = null,

    @SerializedName("repeatPassword")
    var repeatPassword: String? = null



//    @SerializedName("email")
//    var email: String? = null,
//
//    @SerializedName("mobileNumber")
//    var mobileNumber: String? = null,
//
//    @SerializedName("accountNumber")
//    var accountNumber: String? = null
)
