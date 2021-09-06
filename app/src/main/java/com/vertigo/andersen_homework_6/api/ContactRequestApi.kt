package com.vertigo.andersen_homework_6.api

import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.data.ContactApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ContactRequestApi {
    @GET("v2/list?page=9&limit=101")
    suspend fun getContactList(): List<ContactApi>
}

object ContactApiImpl {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl("https://picsum.photos/")
        .build()

    private val contactApiService = retrofit.create(ContactRequestApi::class.java)

    suspend fun getContactList(): List<Contact> {
        var count = 0
        return withContext(Dispatchers.Default) {
            contactApiService.getContactList().map {
                result ->
                count++
                // поле author бывает разным. Может содержать только имя, либо имя с двумя пробелами
                val _name = mutableListOf<String>()
                _name.addAll(result.name.split(" "))

                if (_name.size == 1) {
                    _name.add("No second name")
                } else if (_name.size > 2) {
                    _name[1] = _name[2]
                }
                Contact(count,
                    _name[0],
                    _name[1],
                    result.photoUrl,
                    result.phone)
            }
        }
    }
}