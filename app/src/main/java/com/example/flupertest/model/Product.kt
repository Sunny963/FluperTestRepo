package com.example.flupertest.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Product")
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = false) var Id: String,
    @ColumnInfo var name: String,
    @ColumnInfo var description: String,
    @ColumnInfo @SerializedName("sale price") var salePrice: String,
    @ColumnInfo @SerializedName("regular price") var regularPrice: String,
    @ColumnInfo @SerializedName("product photo") var productPhoto: String
) : Parcelable {
}

fun product() {}