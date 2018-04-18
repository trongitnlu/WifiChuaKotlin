package com.android.nvtrong.wifichuakotlin.model

import android.net.wifi.ScanResult
import android.os.Parcel
import android.os.Parcelable

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by nvtrong on 4/16/2018.
 */

@IgnoreExtraProperties
class WifiStation : Parcelable {
    var ssid: String? = null
    var bssid: String? = null
    var password: String? = null
    var frequency: Int = 0
    var level: Int = 0

    constructor(ssid: String, bssid: String, frequency: Int, level: Int) {
        this.ssid = ssid
        this.bssid = bssid
        this.frequency = frequency
        this.level = level
    }

    constructor() {}

    protected constructor(`in`: Parcel) {
        ssid = `in`.readString()
        bssid = `in`.readString()
        frequency = `in`.readInt()
        level = `in`.readInt()
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(ssid)
        parcel.writeString(bssid)
        parcel.writeInt(frequency)
        parcel.writeInt(level)
    }

    override fun toString(): String {
        return "WifiStation(ssid=$ssid, bssid=$bssid, password=$password, frequency=$frequency, level=$level)"
    }

    companion object {

        val CREATOR: Parcelable.Creator<WifiStation> = object : Parcelable.Creator<WifiStation> {
            override fun createFromParcel(`in`: Parcel): WifiStation {
                return WifiStation(`in`)
            }

            override fun newArray(size: Int): Array<WifiStation?> {
                return arrayOfNulls(size)
            }
        }

        fun newInstance(sr: ScanResult): WifiStation {
            return WifiStation(sr.SSID, sr.BSSID, sr.frequency, sr.level)
        }

        fun newList(srs: List<ScanResult>): List<WifiStation> {
            return srs.map { WifiStation.newInstance(it) }
        }
    }

}
