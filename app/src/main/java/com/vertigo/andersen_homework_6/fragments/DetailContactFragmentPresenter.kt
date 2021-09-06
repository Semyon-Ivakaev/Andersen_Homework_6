package com.vertigo.andersen_homework_6.fragments

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.R
import com.vertigo.andersen_homework_6.data.Contact

class DetailContactFragmentPresenter {
    private var detailContactFragment: DetailContactFragment? = null

    fun setData(data: Contact, mainPhoto: ImageView) {
        setImage(data.photoUrl, mainPhoto)
        detailContactFragment?.setAllViews(data)
    }

    fun saveData(name: String, secondName: String, phoneNumber: String, contact: Contact) {
        for (element in MainActivity.listContacts) {
            if (element.id == contact.id) {
                element.name = name
                element.secondName = secondName
                element.phone = phoneNumber
            }
        }
    }

    private fun setImage(url: String, mainPhoto: ImageView) {
        detailContactFragment?.context?.let {
            Glide.with(it)
                .load(url)
                .centerCrop()
                .error(R.drawable.ic_baseline_error_24)
                .into(mainPhoto)
        }
    }

    fun attachView(view: DetailContactFragment) {
        this.detailContactFragment = view
    }

    fun detachView() {
        this.detailContactFragment = null
    }
}