package com.vertigo.andersen_homework_6.fragments

import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.api.ContactApiImpl
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.recyclers.ContactAdapter
import com.vertigo.andersen_homework_6.recyclers.CustomItemDecorator
import kotlinx.coroutines.*

class ContactsFragmentPresenter {
    private var contactsFragment: ContactsFragment? = null

    private val scopeIO = CoroutineScope(Dispatchers.IO + Job())
    private val scopeDef = CoroutineScope(Dispatchers.Default + Job())
    var clicked: ContactsFragmentClickListener? = null

    private var displayList = mutableListOf<Contact>()
    var showList = mutableListOf<Contact>()

    fun initList() {
        if (MainActivity.listContacts.isEmpty()) {
            scopeIO.launch {
                MainActivity.listContacts = ContactApiImpl.getContactList()
            }
        }
    }

    fun setOnRecycler(recyclerView: RecyclerView) {
        var count = 0
        Log.v("App", "SetOnRecycler")
        CoroutineScope(Dispatchers.IO).launch {
            while (MainActivity.listContacts.isEmpty()) {
                delay(250)
                count++
                if (count > 50) break
            }

            if (MainActivity.listContacts.isNotEmpty()) {
                displayList.addAll(MainActivity.listContacts)
                showList.addAll(MainActivity.listContacts)
                CoroutineScope(Dispatchers.Main).launch {
                    recyclerView.adapter = showList.let {
                        ContactAdapter(it, object : ContactsFragmentClickListener {
                            override fun onContactClickListener(element: Contact) {
                                clicked?.onContactClickListener(element)
                            }
                        })
                    }
                    recyclerView.layoutManager = GridLayoutManager(contactsFragment?.context, 1, RecyclerView.VERTICAL, false)
                    recyclerView.addItemDecoration(CustomItemDecorator())
                    recyclerView.addItemDecoration(DividerItemDecoration(contactsFragment?.context, DividerItemDecoration.VERTICAL))
                }
            }
        }
    }

    fun searchNewData(request: String?, recyclerView: RecyclerView) {
        if (request!!.isNotEmpty()) {
            showList.clear()
            MainActivity.listContacts.forEach {
                if (it.name.lowercase().contains(request) || it.secondName?.lowercase()?.contains(request) == true) {
                    showList.add(it)
                }
            }
        } else {
            showList.clear()
            showList.addAll(MainActivity.listContacts)
        }
        recyclerView.adapter?.notifyDataSetChanged()
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