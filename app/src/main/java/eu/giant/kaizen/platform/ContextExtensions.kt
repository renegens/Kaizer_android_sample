package eu.giant.kaizen.platform

import android.content.Context
import android.widget.Toast
import androidx.annotation.MainThread

@MainThread
fun Context.errorToast(message: String) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
