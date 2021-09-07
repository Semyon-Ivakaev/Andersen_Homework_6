package com.vertigo.andersen_homework_6.recyclers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.andersen_homework_6.R
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.fragments.ContactsFragmentClickListener

class ContactAdapter(private var list: List<Contact>, private val clickListener: ContactsFragmentClickListener?):
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.contact_view_holder, parent, false), clickListener
        )
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun refreshList(newList: List<Contact>) {
        Log.v("App", "Refresh")
        val diffUtil = ContactDiffUtil(list, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        list = newList
        diffResult.dispatchUpdatesTo(this)
    }
}
