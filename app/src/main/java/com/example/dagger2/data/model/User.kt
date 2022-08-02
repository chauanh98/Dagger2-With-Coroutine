package com.example.dagger2.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(
    tableName = "users",
)
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val gender: String = "",
    val rank: Int = 0,
    val country: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val age: Int = 0,
    val points: Long,
    var isFavorite : Boolean = false
) : Parcelable