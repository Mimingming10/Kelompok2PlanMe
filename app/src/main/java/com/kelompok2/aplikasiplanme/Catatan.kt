package com.kelompok2.aplikasiplanme

import android.os.Parcel
import android.os.Parcelable

data class Catatan(val judul: String, val isi: String, val dateline: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(judul)
        parcel.writeString(isi)
        parcel.writeString(dateline)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Catatan> {
        override fun createFromParcel(parcel: Parcel): Catatan {
            return Catatan(parcel)
        }

        override fun newArray(size: Int): Array<Catatan?> {
            return arrayOfNulls(size)
        }
    }
}
