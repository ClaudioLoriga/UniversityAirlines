package com.example.universityairlines.airportlist

import android.os.Parcel
import android.os.Parcelable

class Airport() : Parcelable {

    var code: String? = null
    var name: String? = null
    var citycode: String? = null
    var city: String? = null
    var countrycode: String? = null
    var country: String? = null
    var continent: String? = null

    constructor(parcel: Parcel) : this() {
        code = parcel.readString()
        name = parcel.readString()
        citycode = parcel.readString()
        city = parcel.readString()
        countrycode = parcel.readString()
        country = parcel.readString()
        continent = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(code)
        parcel.writeString(name)
        parcel.writeString(citycode)
        parcel.writeString(city)
        parcel.writeString(countrycode)
        parcel.writeString(country)
        parcel.writeString(continent)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Airport> {
        override fun createFromParcel(parcel: Parcel): Airport {
            return Airport(parcel)
        }

        override fun newArray(size: Int): Array<Airport?> {
            return arrayOfNulls(size)
        }
    }

}