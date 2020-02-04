package com.vilo.muradyan.task.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag (
val id:String?,
val type:String?,
val webTitle:String?,
val webUrl:String?,
val apiUrl:String?,
val bio:String?,
val bylineImageUrl:String?,
val bylineLargeImageUrl:String?,
val firstName:String?,
val lastName:String?,
val twitterHandle:String?,
val sectionId:String?,
val sectionName:String?
):Parcelable