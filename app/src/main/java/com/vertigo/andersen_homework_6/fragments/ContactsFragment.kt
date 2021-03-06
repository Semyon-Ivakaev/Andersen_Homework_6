package com.vertigo.andersen_homework_6.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.andersen_homework_6.databinding.ContactsFragmentBinding

class ContactsFragment: Fragment() {
    var contactsFragmentPresenter: ContactsFragmentPresenter? = null

    lateinit var binding: ContactsFragmentBinding
    private var clickListener: ContactsFragmentClickListener? = null
    var recycler: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ContactsFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        contactsFragmentPresenter = ContactsFragmentPresenter()
        contactsFragmentPresenter?.attachView(this)
        contactsFragmentPresenter?.initList()

        with(binding) {
            recycler = mainRecycler
            contactsFragmentPresenter?.setOnRecycler(mainRecycler)
            contactsFragmentPresenter?.clicked = clickListener
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String): Boolean {
                    searchView.clearFocus()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    contactsFragmentPresenter?.searchNewData(newText, mainRecycler)

                    return false
                }
            })
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContactsFragmentClickListener) {
            clickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        contactsFragmentPresenter?.detachView()
        clickListener = null
    }
}