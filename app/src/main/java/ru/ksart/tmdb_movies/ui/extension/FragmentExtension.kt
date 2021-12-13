package ru.ksart.tmdb_movies.ui.extension

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes stringId: Int) {
    if (stringId == -1) return
    Toast.makeText(requireContext(), stringId, Toast.LENGTH_SHORT).show()
}

// показать тост строковый
fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}
