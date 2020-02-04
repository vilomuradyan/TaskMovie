package com.vilo.muradyan.task.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "results")
data class Results (
	@PrimaryKey
	@SerializedName("popularity") val popularity : Double,
	@SerializedName("id") val id : Int,
	@SerializedName("video") val video : Boolean,
	@SerializedName("vote_count") val vote_count : Int,
	@SerializedName("vote_average") val vote_average : Double,
	@SerializedName("title") val title : String?,
	@SerializedName("release_date") val release_date : String?,
	@SerializedName("original_language") val original_language : String?,
	@SerializedName("original_title") val original_title : String?,
	@SerializedName("backdrop_path") val backdrop_path : String?,
	@SerializedName("adult") var adult : Boolean,
	@SerializedName("overview") val overview : String?,
	@SerializedName("poster_path") val poster_path : String?
) : Parcelable