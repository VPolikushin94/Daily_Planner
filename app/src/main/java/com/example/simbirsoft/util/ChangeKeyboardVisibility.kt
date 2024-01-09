package com.example.simbirsoft.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun changeKeyboardVisibility(isVisible: Boolean, context: Context, editText: EditText) {
    val keyboard =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (isVisible) {
        keyboard.showSoftInput(editText, 0)
    } else {
        keyboard.hideSoftInputFromWindow(editText.windowToken, 0)
    }
}
