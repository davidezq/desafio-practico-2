package com.example.desafiopractio2.promedio

import android.os.Parcel
import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView

class AlumnoApadter() :RecyclerView.Adapter<AlumnoViewHolder>(), Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlumnoApadter> {
        override fun createFromParcel(parcel: Parcel): AlumnoApadter {
            return AlumnoApadter(parcel)
        }

        override fun newArray(size: Int): Array<AlumnoApadter?> {
            return arrayOfNulls(size)
        }
    }
}