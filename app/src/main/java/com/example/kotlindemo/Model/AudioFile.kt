package com.example.kotlindemo.Model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class AudioFile(
    val id: Long,
    val title: String?,
    val duration: Long, // duration in milliseconds
    val size: Long,     // size in bytes
    val artist: String?,
    val uri: Uri?,
    val albumArtUri: Uri?,
    var isFavorite: Boolean
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeLong(duration)
        parcel.writeLong(size)
        parcel.writeString(artist)
        parcel.writeParcelable(uri, flags)
        parcel.writeParcelable(albumArtUri, flags)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AudioFile> {
        override fun createFromParcel(parcel: Parcel): AudioFile {
            return AudioFile(parcel)
        }

        override fun newArray(size: Int): Array<AudioFile?> {
            return arrayOfNulls(size)
        }
    }

}


