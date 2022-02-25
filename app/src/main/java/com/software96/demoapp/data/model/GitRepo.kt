package com.software96.demoapp.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GitRepo(
    @SerializedName("id")
    @Expose
    val id: Long,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("full_name")
    @Expose
    val fullName: String,
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("html_url")
    @Expose
    val url: String,
    @SerializedName("stargazers_count")
    @Expose
    val stars: Int,
    @SerializedName("forks_count")
    @Expose
    val forks: Int,
    @SerializedName("language")
    @Expose
    val language: String?,
    @SerializedName("watchers")
    @Expose
    val watchers: Int,
    @SerializedName("owner")
    @Expose
    val owner: Owner,
    @SerializedName("created_at")
    @Expose
    val createDate: String,
    @SerializedName("updated_at")
    @Expose
    val updateDate: String,
    @SerializedName("open_issues")
    @Expose
    val openIssues: Int
) : Parcelable {
    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<GitRepo>() {
            override fun areItemsTheSame(oldItem: GitRepo, newItem: GitRepo) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GitRepo, newItem: GitRepo) =
                oldItem == newItem
        }
    }
}