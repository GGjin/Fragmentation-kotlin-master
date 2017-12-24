package me.yokeyword.sample.demo_wechat.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by YoKeyword on 16/6/30.
 */
class Chat : Parcelable {
    lateinit var name: String
    lateinit var message: String
    var time: Long = 0
    var avatar: Int = 0

    constructor() {}

    protected constructor(`in`: Parcel) {
        name = `in`.readString()
        message = `in`.readString()
        time = `in`.readLong()
        avatar = `in`.readInt()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeString(message)
        dest.writeLong(time)
        dest.writeInt(avatar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Chat> = object : Parcelable.Creator<Chat> {
            override fun createFromParcel(`in`: Parcel): Chat {
                return Chat(`in`)
            }

            override fun newArray(size: Int): Array<Chat?> {
                return arrayOfNulls(size)
            }
        }
    }
}
