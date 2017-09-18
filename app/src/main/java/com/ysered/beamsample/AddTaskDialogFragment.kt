package com.ysered.beamsample

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.ysered.beamsample.di.InjectableFragment
import com.ysered.beamsample.di.ViewModelFactory
import javax.inject.Inject


class AddTaskDialogFragment : DialogFragment(), InjectableFragment {

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var summaryText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        tasksViewModel = viewModelFactory.create(TasksViewModel::class.java)

        val dialog = AlertDialog.Builder(activity)
                .setTitle(R.string.addTask)
                .setView(R.layout.dialog_add_task)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, { _, _ ->
                    val summary = summaryText.text.toString()
                    tasksViewModel.saveTask(summary)
                })
                .create()
        dialog.show()

        summaryText = dialog.findViewById(R.id.summaryText)!!
        val okButton = dialog.findViewById<Button>(android.R.id.button1)!!
        summaryText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) = Unit

            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                okButton.isEnabled = text?.isNotEmpty() ?: false
            }
        })
        return dialog
    }
}
