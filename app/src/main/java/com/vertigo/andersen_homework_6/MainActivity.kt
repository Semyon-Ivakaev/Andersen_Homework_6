package com.vertigo.andersen_homework_6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.fragments.ContactsFragment
import com.vertigo.andersen_homework_6.fragments.ContactsFragmentClickListener
import com.vertigo.andersen_homework_6.fragments.DetailContactClickListener
import com.vertigo.andersen_homework_6.fragments.DetailContactFragment
import com.vertigo.andersen_homework_6.utils.ActionDialog

class MainActivity : AppCompatActivity(), ContactsFragmentClickListener, DetailContactClickListener {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, ContactsFragment())
                .commit()
        }
    }

    companion object {
        var listContacts: MutableList<Contact> = mutableListOf()
    }

    override fun onContactClickListener(element: Contact) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.main_container, DetailContactFragment.newInstance(element))
            .commit()
    }

    override fun onContactHoldListener(element: Contact) {
        ActionDialog(element).show(supportFragmentManager, "TAG")
    }

    override fun onButtonClicked() {
        val count: Int = supportFragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed();
        } else {
            supportFragmentManager.popBackStack()
            // Это делаем для того, чтобы спрятать фрагмент при сохранении или закрытии
            supportFragmentManager.beginTransaction().hide(DetailContactFragment()).commit()
        }
    }
}