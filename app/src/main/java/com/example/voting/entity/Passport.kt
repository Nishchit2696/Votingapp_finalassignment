package com.example.voting.entity

import android.os.Parcel
import android.os.Parcelable

data class Passport (
        val _id : String? = null,
        val simage : String? = null,
        val Firstname : String? = null,
        val Lastname : String? = null,
        val Fathername: String?=null,
        val CitizenshipNo : String? = null,
        val Ocupation : String? = null,
        val Education : String? = null,
        val Phonenumber : String? = null
        ) : Parcelable{
        constructor(parcel: Parcel) : this(
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
                parcel.writeString(Fathername)
                parcel.writeString(CitizenshipNo)
                parcel.writeString(Ocupation)
                parcel.writeString(Education)
                parcel.writeString(Phonenumber)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<Passport> {
                override fun createFromParcel(parcel: Parcel): Passport {
                        return Passport(parcel)
                }

                override fun newArray(size: Int): Array<Passport?> {
                        return arrayOfNulls(size)
                }
        }
}