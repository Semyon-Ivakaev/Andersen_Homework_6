package com.vertigo.andersen_homework_6.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.vertigo.andersen_homework_6.MainActivity
import com.vertigo.andersen_homework_6.R
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.data.ContactService
import com.vertigo.andersen_homework_6.fragments.ContactsFragment

class ActionDialog(val element: Contact): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Change action:")
                .setPositiveButton("Delete",
                DialogInterface.OnClickListener {
                        dialog, id -> })
                .setNegativeButton("Cancel", 
                DialogInterface.OnClickListener{ dialog, which ->  })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun removeElement() {
        val newList = MainActivity.listContacts
        for (i in 0 until newList.size - 1) {
            if (newList[i].id == element.id) {
                newList.removeAt(i)
                MainActivity.listContacts = newList
            }
        }
        Log.v("app", ContactService().getContacts().toString())
    }
}