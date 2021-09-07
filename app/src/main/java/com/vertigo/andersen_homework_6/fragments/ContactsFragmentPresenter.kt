package com.vertigo.andersen_homework_6.fragments

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.api.ContactApiImpl
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.data.ContactListener
import com.vertigo.andersen_homework_6.data.ContactService
import com.vertigo.andersen_homework_6.recyclers.ContactAdapter
import kotlinx.coroutines.*

class ContactsFragmentPresenter {
    private var contactsFragment: ContactsFragment? = null

    private val scopeIO = CoroutineScope(Dispatchers.IO + Job())
    private val scopeDef = CoroutineScope(Dispatchers.Default + Job())
    var clicked: ContactsFragmentClickListener? = null

    private var displayList = mutableListOf<Contact>()
    var showList = mutableListOf<Contact>()
    var recycler: RecyclerView? = null
    private lateinit var adapter: ContactAdapter

    private val contactService: ContactService
        get() {
            Log.v("App", "----------------------")
            return ContactService()
        }

    private val contactListener: ContactListener = { adapter.contacts = it}

    fun initList() {
/*        if (MainActivity.listContacts.isEmpty()) {
            scopeIO.launch {
                MainActivity.listContacts = ContactApiImpl.getContactList()
            }
        }*/
        if (ContactService().getContacts().size < 1) {
            scopeIO.launch {
//                val contact = ContactService()
                Log.v("App", "initList")
            }
        }
    }

    fun setOnRecycler(recyclerView: RecyclerView) {
        recycler = recyclerView

        var count = 0
        Log.v("App", "SetOnRecycler")
        CoroutineScope(Dispatchers.IO).launch {
            /*while (MainActivity.listContacts.isEmpty()) {
                delay(250)
                count++
                if (count > 50) break
            }*/
            while (ContactService().getContacts().size < 1) {
                delay(250)
                count++
                if (count > 50) break
            }
            Log.v("App", ContactService().getContacts().toString())
            if (MainActivity.listContacts.isNotEmpty()) {
                displayList.addAll(MainActivity.listContacts)
                showList.addAll(MainActivity.listContacts)
                CoroutineScope(Dispatchers.Main).launch {
                    adapter = showList.let {
                        ContactAdapter(it, object : ContactsFragmentClickListener {
                            override fun onContactClickListener(element: Contact) {
                                clicked?.onContactClickListener(element)
                            }

                            override fun onContactHoldListener(element: Contact) {
                                clicked?.onContactHoldListener(element)
                            }
                        })
                    }
                    recyclerView.layoutManager = GridLayoutManager(contactsFragment?.context, 1, RecyclerView.VERTICAL, false)
                    contactService.addListener(contactListener)
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
        contactService.removeListener(contactListener)
    }
}