package com.ramgdev.shoppa.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorites_table")
data class FavoritesEntity(
    val category: String,
    val description: String,
    val image: String,
    val price: Double,
    val title: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val liked: Boolean = false
): Parcelable
