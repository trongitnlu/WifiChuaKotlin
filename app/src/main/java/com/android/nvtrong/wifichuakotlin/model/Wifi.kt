//package com.android.nvtrong.wifichuakotlin.model
//
//import android.net.wifi.ScanResult
//import android.os.Parcel
//import android.os.Parcelable
//
//import com.google.firebase.database.IgnoreExtraProperties
//
///**
// * Created by nvtrong on 4/16/2018.
// */
//
//@IgnoreExtraProperties
//class Wifi : Parcelable {
//    private var ssid: String? = null
//    private var bssis: String? = null
//    private var frequency: Int = 0
//    private var level: Int = 0
//
//    constructor(ssid: String, bssis: String, frequency: Int, level: Int) {
//        this.ssid = ssid
//        this.bssis = bssis
//        this.frequency = frequency
//        this.level = level
//    }
//
//    constructor() {}
//
//    protected constructor(`in`: Parcel) {
//        ssid = `in`.readString()
//        bssis = `in`.readString()
//        frequency = `in`.readInt()
//        level = `in`.readInt()
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(parcel: Parcel, i: Int) {
//        parcel.writeString(ssid)
//        parcel.writeString(bssis)
//        parcel.writeInt(frequency)
//        parcel.writeInt(level)
//    }
//
//    companion object {
//
//        val CREATOR: Parcelable.Creator<Wifi> = object : Parcelable.Creator<Wifi> {
//            override fun createFromParcel(`in`: Parcel): Wifi {
//                return Wifi(`in`)
//            }
//
//            override fun newArray(size: Int): Array<Wifi?> {
//                return arrayOfNulls(size)
//            }
//        }
//
//        fun newInstance(sr: ScanResult): Wifi {
//            return Wifi(sr.SSID, sr.BSSID, sr.frequency, sr.level)
//        }
//
//        fun newList(srs: List<ScanResult>): List<WifiStation> {
//            return srs.map { WifiStation.newInstance(it) }
//        }
//    }
//}
