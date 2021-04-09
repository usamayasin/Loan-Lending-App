package org.jethro.mobile.models.payload

import android.os.Parcelable

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Rajan Maurya on 10/03/17.
 */

@Parcelize
data class ThirdPartyTransferPayload(
        @SerializedName("fromOfficeId")
        var fromOfficeId: Int? = null,

        @SerializedName("fromClientId")
        var fromClientId: Long? = null,

        @SerializedName("fromAccountType")
        var fromAccountType: Int? = null,

        @SerializedName("fromAccountId")
        var fromAccountId: String? = null,

        @SerializedName("toOfficeId")
        var toOfficeId: Int? = null,

        @SerializedName("toClientId")
        var toClientId: Long? = null,

        @SerializedName("toAccountType")
        var toAccountType: Int? = null,

        @SerializedName("toAccountId")
        var toAccountId: String? = null,

        @SerializedName("transferDate")
        var transferDate: String? = null,

        @SerializedName("transferAmount")
        var transferAmount: String? = null,

        @SerializedName("transferDescription")
        var transferDescription: String? = null,

        @SerializedName("dateFormat")
        var dateFormat : String = "dd MMMM yyyy",

        var locale : String = "en",

        @SerializedName("fromAccountNumber")
        @Transient
        var fromAccountNumber: String? = null,

        @SerializedName("toAccountNumber")
        @Transient
        var toAccountNumber: String? = null

) : Parcelable