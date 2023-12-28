package com.danan.mynotes.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class MataKuliah (
    @StringRes val nama : Int,
    @StringRes val sks : Int,
    @DrawableRes val logo : Int
)