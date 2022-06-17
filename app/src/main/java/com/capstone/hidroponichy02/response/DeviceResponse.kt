package com.capstone.hidroponichy02.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class DeviceResponse(


    @field:SerializedName("data")
    val data: List<DataItem>,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("status")
    val status: String
)

@Entity(tableName = "device")
data class DataItem(

    @field:SerializedName("product")
    val product: Product,

    @field:SerializedName("planted")
    val planted: Planted,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("status")
    val statuss: Int,

    @PrimaryKey
    @field:SerializedName("id")
    val id: String
)

data class Product(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String
)
data class Planted(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String
)