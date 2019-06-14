package com.mangesh.gitexpo.Pojo

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthToken() :Parcelable {


    @SerializedName("access_token")
    @Expose
    var accessToken: String? = null

    @SerializedName("access_type")
    @Expose
    var accessType: String? = null

    constructor(parcel: Parcel) : this() {
        accessToken = parcel.readString()
        accessType = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(accessToken)
        parcel.writeString(accessType)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "AuthToken(accessToken=$accessToken, accessType=$accessType)"
    }

    companion object CREATOR : Parcelable.Creator<AuthToken> {
        override fun createFromParcel(parcel: Parcel): AuthToken {
            return AuthToken(parcel)
        }

        override fun newArray(size: Int): Array<AuthToken?> {
            return arrayOfNulls(size)
        }
    }
}
