package com.vertigo.andersen_homework_6.recyclers

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.databinding.ContactViewHolderBinding
import com.vertigo.andersen_homework_6.fragments.ContactsFragmentClickListener

class ContactViewHolder(private var view: View, private val clickListener: ContactsFragmentClickListener?): RecyclerView.ViewHolder(view),
View.OnLongClickListener{
    private val binding = ContactViewHolderBinding.bind(view)
    private var elementContact: Contact? = null

    init {
        itemView.setOnLongClickListener(this)
    }

    fun bind(element: Contact) {
        with(binding) {
            firstName.text = element.name
            secondName.text = element.secondName
            phoneNumber.text = element.phone
            loadImage(element.photoUrl, imageContact)
            elementContact = element

            contactRecyclerElement.setOnClickListener {
                clickListener?.onContactClickListener(element)
            }
        }
    }

    private fun loadImage(url: String, imageContact: ImageView) {
        Glide.with(itemView)
            .load(url)
            .into(imageContact)

    }

    override fun onLongClick(v: View?): Boolean {
        elementContact?.let { clickListener?.onContactHoldListener(it) }
        return true
    }
}