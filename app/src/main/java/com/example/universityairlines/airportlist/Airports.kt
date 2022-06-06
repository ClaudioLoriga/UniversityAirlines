package com.example.universityairlines.airportlist

import android.os.Parcel
import android.os.Parcelable


class Airports() : Parcelable {
    var airports: List<Airport>? = null

    constructor(parcel: Parcel) : this() {
        airports = parcel.createTypedArrayList(Airport)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(airports)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Airports> {
        override fun createFromParcel(parcel: Parcel): Airports {
            return Airports(parcel)
        }

        override fun newArray(size: Int): Array<Airports?> {
            return arrayOfNulls(size)
        }
    }

}