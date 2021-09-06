package com.vertigo.andersen_homework_6.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vertigo.andersen_homework_6.data.Contact
import com.vertigo.andersen_homework_6.databinding.DetailContactFragmentBinding

class DetailContactFragment: Fragment() {
    private var detailContactFragmentPresenter = DetailContactFragmentPresenter()

    private lateinit var binding: DetailContactFragmentBinding
    private var detailContactClickListener: DetailContactClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailContactFragmentBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        detailContactFragmentPresenter.attachView(this)

        val mainData = arguments?.getSerializable("contact") as Contact

        detailContactFragmentPresenter.setData(mainData, binding.mainPhoto)
        initButtons(mainData)

        return view
    }

    fun setAllViews(contact: Contact) {
        with(binding) {
            nameContact.setText(contact.name, TextView.BufferType.EDITABLE)
            secondNameContact.setText(contact.secondName, TextView.BufferType.EDITABLE)
            phoneNumberContact.setText(contact.phone, TextView.BufferType.EDITABLE)

        }
    }

    private fun initButtons(contact: Contact) {
        with(binding) {
            cancelButton.setOnClickListener {
                detailContactClickListener?.onButtonClicked()
            }
            editButton.setOnClickListener {
                detailContactFragmentPresenter.saveData(
                    nameContact.text.toString(),
                    secondNameContact.text.toString(),
                    phoneNumberContact.text.toString(),
                    contact)
                detailContactClickListener?.onButtonClicked()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DetailContactClickListener) {
            detailContactClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        detailContactClickListener = null
        detailContactFragmentPresenter.detachView()
    }

    companion object {
        fun newInstance(element: Contact): DetailContactFragment {
            val args = Bundle()
            args.putSerializable("contact", element)
            val fragment = DetailContactFragment()
            fragment.arguments = args
            return fragment
        }
    }
}