package com.vertigo.andersen_homework_6.data

import android.util.Log
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.api.ContactApiImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

typealias ContactListener = (contacts: List<Contact>) -> Unit

class ContactService {
    private val scopeIO = CoroutineScope(Dispatchers.IO + Job())
    private var contacts = mutableListOf<Contact>()
    private val listeners = mutableSetOf<ContactListener>()

    init {
        scopeIO.launch {
            contacts = ContactApiImpl.getContactList()
            Log.v("app", "init Service")
        }
    }

    fun getContacts(): List<Contact> {
        return contacts
    }

    fun deleteContact(contact: Contact) {
        val index = contacts.indexOfFirst { it.id == contact.id }
        contacts.removeAt(index)
        listeners.forEach { it.invoke(contacts) }
    }

    fun addListener(listener: ContactListener) {
        listeners.add(listener)
        listener.invoke(contacts)
    }

    fun removeListener(listener: ContactListener) {
        listeners.remove(listener)
    }

   /* private fun notifyChanges() {
        listeners.forEach { it.invoke(contacts) }
    }*/
}