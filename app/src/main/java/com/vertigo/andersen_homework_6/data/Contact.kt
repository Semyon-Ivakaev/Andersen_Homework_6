package com.vertigo.andersen_homework_6.data

import java.io.Serializable

/**
 * secondName - некоторые авторы фото не имеют фамилии, то есть в поле author 1 элемент,
 * поэтому вставляем дефолтное значение, которое заменяем
 */
data class Contact(
    val id: Int,
    var name: String,
    var secondName: String? = "No second name",
    val photoUrl: String,
    var phone: String
): Serializable