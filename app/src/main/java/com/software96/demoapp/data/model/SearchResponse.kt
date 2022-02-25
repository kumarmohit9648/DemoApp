package com.software96.demoapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    @SerializedName("total_count")
    @Expose
    val total: Int = 0,
    @SerializedName("items")
    @Expose
    val items: List<GitRepo> = emptyList(),
) : Parcelable