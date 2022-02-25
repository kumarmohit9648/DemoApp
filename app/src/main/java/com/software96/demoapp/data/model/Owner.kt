package com.software96.demoapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Owner(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("login")
    @Expose
    val login: String,
    @SerializedName("avatar_url")
    @Expose
    val avatar_url: String
) : Parcelable