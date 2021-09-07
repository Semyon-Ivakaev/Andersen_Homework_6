package com.vertigo.andersen_homework_6.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.R
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.fragments.ContactsFragmentClickListener
import java.util.*

class ContactAdapter(private val list: List<Contact>, private val clickListener: ContactsFragmentClickListener):
    RecyclerView.Adapter<ContactViewHolder>() {

    var contacts: List<Contact> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

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
}

class ContactDiffUtil(
    private val oldList: List<Contact>,
    private val newList: List<Contact>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }

}