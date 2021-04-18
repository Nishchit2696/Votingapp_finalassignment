package com.example.voting.entity

import android.os.Parcel
import android.os.Parcelable

data class License (
    val _id : String? = null,
    val simage : String? = null,
    val Firstname : String? = null,
    val Lastname : String? = null,
    val BloodGroup: String?=null,
    val Ocupation : String? = null,
    val Education : String? = null,
    val Province : String? = null,
    val City : String? = null,
    val Phonenumber : String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(simage)
        parcel.writeString(Firstname)
        parcel.writeString(Lastname)
        parcel.writeString(BloodGroup)
        parcel.writeString(Ocupation)
        parcel.writeString(Education)
        parcel.writeString(Province)
        parcel.writeString(City)
        parcel.writeString(Phonenumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<License> {
        override fun createFromParcel(parcel: Parcel): License {
            return License(parcel)
        }

        override fun newArray(size: Int): Array<License?> {
            return arrayOfNulls(size)
        }
    }

}