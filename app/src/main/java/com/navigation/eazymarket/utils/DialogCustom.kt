package com.navigation.eazymarket.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.navigation.eazymarket.R
import com.navigation.eazymarket.database.AppDatabase
import com.navigation.eazymarket.domain.SupermarketProductJoin
import kotlinx.android.synthetic.main.dialog_with_input.view.*
import kotlinx.android.synthetic.main.dialog_with_input_and_swtch.view.*

class DialogCustom(val context: Context) {

    companion object {
        fun getInstance(context: Context): DialogCustom {
            return DialogCustom(context)
        }
    }

    fun showDialogGeneric(
        title: String,
        message: String,
        namePositiveButton: String,
        nameNegativeButton: String,
        function: (code: Int) -> Unit,
        codeFunctionPositive: Int,
        CodeFunctionNegative: Int
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
            .setPositiveButton(namePositiveButton) { _, _ ->
                function(codeFunctionPositive)
            }
            .setNegativeButton(nameNegativeButton) { _, _ ->
                function(CodeFunctionNegative)
            }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showDialogWithInput(
        inflater: LayoutInflater,
        title: String,
        message: String,
        value: Double,
        namePositiveButton: String,
        nameNegativeButton: String,
        function: (value: String, supermarketProductJoin: SupermarketProductJoin) -> Boolean,
        supermarketProductJoin: SupermarketProductJoin
    ) {
        val builder = AlertDialog.Builder(context)
        val view = inflater.inflate(R.layout.dialog_with_input, null)
        view.textview_name_product_diolog.text = message
        view.textview_title.text = title
        view.textInputValueDialog.setText(value.toString())

        builder.setView(view)
        builder.setPositiveButton(namePositiveButton, null)
        builder.setNegativeButton(nameNegativeButton, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val value = view.textInputValueDialog.text.toString().trim()
            if (validateFieldEmpty(value)) {
                dialog.dismiss()
                function(view.textInputValueDialog.text.toString(), supermarketProductJoin)
            } else {
                view.textInputValueDialog.error = "Campo vazio ou igual a 0"
                view.textInputValueDialog.requestFocus()
            }
        }

    }

    fun showDialogWithInputAndSwith(
        inflater: LayoutInflater,
        title: String,
        message: String,
        value: Double,
        namePositiveButton: String,
        nameNegativeButton: String,
        saveInSupermarket: (SupermarketProductJoin) -> Unit,
        supermarketProductJoin: SupermarketProductJoin
    ) {

        val builder = AlertDialog.Builder(context)
        val view = inflater.inflate(R.layout.dialog_with_input_and_swtch, null)
        view.textview_name_input_switch.text = message
        view.textview_title_input_switch.text = title
        view.textInputValueDialogInputSwitch.setText(value.toString())

        view.switch_add_car_dialog_input_switch.setOnCheckedChangeListener { _, isChecked ->
            view.switch_add_car_dialog_input_switch.text =
                if (isChecked) "Item adicionado" else "Item não adicionado"
        }
        builder.setView(view)
        builder.setPositiveButton(namePositiveButton, null)
        builder.setNegativeButton(nameNegativeButton, null)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            val value = view.textInputValueDialogInputSwitch.text.toString().trim()
            if (validateFieldEmpty(value)) {
                dialog.dismiss()
                if (view.switch_add_car_dialog_input_switch.isChecked){
                    supermarketProductJoin.quantity = 1
                    setUsingSupermarket(supermarketProductJoin.supermarket, true)
                }
                supermarketProductJoin.valueProdut = value.toDouble()
                saveInSupermarket(supermarketProductJoin)

            } else {
                view.textInputValueDialogInputSwitch.error = "Campo vazio ou igual a 0"
                view.textInputValueDialogInputSwitch.requestFocus()
            }
        }
    }

    private fun validateFieldEmpty(value: String): Boolean {
        if (value.isNotEmpty()) {
            if (value.toDouble() != 0.0) {
                return true
            }
        }
        return false
    }

    private fun setUsingSupermarket(id: Long, isUsed: Boolean){
        AppDatabase(context).supermarketDao().setUsingSupermarket(id, isUsed)
    }
}

