package com.vertigo.andersen_homework_6.fragments

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.api.ContactApiImpl
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.recyclers.ContactAdapter
import kotlinx.coroutines.*

class ContactsFragmentPresenter {
    private var contactsFragment: ContactsFragment? = null

    private val scopeIO = CoroutineScope(Dispatchers.IO + Job())
    private val scopeDef = CoroutineScope(Dispatchers.Default + Job())
    var clicked: ContactsFragmentClickListener? = null

    fun initList() {
        if (MainActivity.listContacts.isEmpty()) {
            scopeIO.launch {
                MainActivity.listContacts = ContactApiImpl.getContactList()
            }
        }
    }

    fun setOnRecycler(recyclerView: RecyclerView) {
        var count = 0
        CoroutineScope(Dispatchers.IO).launch {
            while (MainActivity.listContacts.isEmpty()) {
                delay(250)
                count++
                if (count > 50) break
            }

            if (MainActivity.listContacts.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = MainActivity.listContacts.let {
                        ContactAdapter(it, object : ContactsFragmentClickListener {
                            override fun onContactClickListener(element: Contact) {
                                clicked?.onContactClickListener(element)
                            }
                        })
                    }
                    recyclerView.layoutManager = GridLayoutManager(contactsFragment?.context, 1, RecyclerView.VERTICAL, false)
                }
            }
        }

    }

    fun attachView(view: ContactsFragment) {
        this.contactsFragment = view
    }

    fun detachView() {
        this.contactsFragment = null
        scopeIO.cancel()
        scopeDef.cancel()
    }
}