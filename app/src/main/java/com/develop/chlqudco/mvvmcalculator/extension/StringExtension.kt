package com.develop.chlqudco.mvvmcalculator.extension

import java.lang.NumberFormatException

fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}