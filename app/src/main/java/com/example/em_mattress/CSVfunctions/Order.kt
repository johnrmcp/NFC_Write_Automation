package com.example.em_mattress.CSVfunctions

import android.os.Parcelable
import android.os.Parcel

class Order(
    val orderdate: String?,
    val ordernumber: String?,
    val producttype: String?,
    val variant: String?,
    val quantity: String?,
    val name: String?,
    val address: String?,
    val phone: String?,
    val image: String?,
    val music: String?,
    val image2: String?,
    val music2: String?,
    val image3: String?,
    val music3: String?,
    val image4: String?,
    val music4: String?) : Parcelable {
        // auto - generate parcelable code
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
        parcel.writeString(orderdate)
        parcel.writeString(ordernumber)
        parcel.writeString(producttype)
        parcel.writeString(variant)
        parcel.writeString(quantity)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(phone)
        parcel.writeString(image)
        parcel.writeString(music)
        parcel.writeString(image2)
        parcel.writeString(music2)
        parcel.writeString(image3)
        parcel.writeString(music3)
        parcel.writeString(image4)
        parcel.writeString(music4)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Order> {
        override fun createFromParcel(parcel: Parcel): Order {
            return Order(parcel)
        }

        override fun newArray(size: Int): Array<Order?> {
            return arrayOfNulls(size)
        }
    }
}
