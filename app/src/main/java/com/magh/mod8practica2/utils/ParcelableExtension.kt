package com.magh.mod8practica2.utils

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

inline fun <reified T : Parcelable> Bundle.retrieveParcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.retrieveParcelableArrayList(key: String): java.util.ArrayList<T>? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList<T>(key)
}