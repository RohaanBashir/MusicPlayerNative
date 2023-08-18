package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class SongInformation(
    var id: Long,
    var displayName: String,
    var duration: Long,
    var path: String,
    val album: String
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(displayName)
        parcel.writeLong(duration)
        parcel.writeString(path)
        parcel.writeString(album)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongInformation> {
        override fun createFromParcel(parcel: Parcel): SongInformation {
            return SongInformation(parcel)
        }

        override fun newArray(size: Int): Array<SongInformation?> {
            return arrayOfNulls(size)
        }
    }


}