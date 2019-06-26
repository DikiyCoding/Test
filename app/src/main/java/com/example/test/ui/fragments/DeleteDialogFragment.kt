package com.example.test.ui.fragments

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test.R
import com.example.test.utils.CallbackManager.AdapterHistoryCallback
import kotlinx.android.synthetic.main.fragment_dialog_delete.view.*

class DeleteDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_dialog_delete, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.btn_cancel.setOnClickListener { this.dismiss() }
        view.btn_ok.setOnClickListener {
            (activity as AdapterHistoryCallback).onDialogConfirmClick()
            this.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog.window?.setLayout(
            resources.getDimensionPixelSize(R.dimen.dim_dialog_delete_width),
            resources.getDimensionPixelSize(R.dimen.dim_dialog_delete_height)
        )
    }
}
