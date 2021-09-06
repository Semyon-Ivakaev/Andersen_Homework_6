package com.vertigo.andersen_homework_6.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ContactApi
    (@Json(name = "author") var name: String,
     @Json(name= "url") var secondName: String,
     @Json(name = "download_url") val photoUrl: String,
     @Json(name = "id") var phone: String)
