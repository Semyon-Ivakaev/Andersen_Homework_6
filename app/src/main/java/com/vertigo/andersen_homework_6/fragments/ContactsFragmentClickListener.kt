package com.vertigo.andersen_homework_6.fragments

import com.vertigo.andersen_homework_6.data.Contact

interface ContactsFragmentClickListener {
    fun onContactClickListener(element: Contact)

    fun onContactHoldListener(element: Contact)
}