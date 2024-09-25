package com.example.myapplication.domain.entities

import com.example.myapplication.R


data class Currency(
    var name: String? = "",
    var flag: Int? = R.drawable.flag_ad,
    var rate: Double? = null,
    var isBase: Boolean? = false
)
