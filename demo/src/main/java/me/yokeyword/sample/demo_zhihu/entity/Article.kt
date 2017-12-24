package me.yokeyword.sample.demo_zhihu.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by YoKeyword on 16/2/1.
 */
class Article : Parcelable {
    var title: String? = null
    var content: String? = null
    var imgRes: Int = 0

    constructor(title: String, content: String) {
        this.title = title
        this.content = content
    }

    constructor(title: String, imgRes: Int) {
        this.title = title
        this.imgRes = imgRes
    }

    protected constructor(`in`: Parcel) {
        title = `in`.readString()
        content = `in`.readString()
        imgRes = `in`.readInt()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(content)
        dest.writeInt(imgRes)
    }

    companion object {

        val CREATOR: Parcelable.Creator<Article> = object : Parcelable.Creator<Article> {
            override fun createFromParcel(`in`: Parcel): Article {
                return Article(`in`)
            }

            override fun newArray(size: Int): Array<Article?> {
                return arrayOfNulls(size)
            }
        }
    }
}
