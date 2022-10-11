package com.ramgdev.shoppa.util

import android.text.Editable
import android.text.TextWatcher

class CreditCardFormatter(
    private val TOTAL_SYMBOLS: Int = 21, // size of pattern 000-000-000-000-000-0
    private val TOTAL_DIGITS: Int = 16, // max numbers of digits in pattern: 0000 x 4
    private val DIVIDER_MODULO: Int = 4, // means divider position is every 4th symbol beginning with 1
    private val DIVIDER_POSITION: Int = DIVIDER_MODULO - 1, // means divider position is every 3th symbol beginning with 0
    private val DIVIDER: Char = '-'
): TextWatcher {
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        if (!isInputCorrect(s!!, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER)) {
            s?.replace(0, s.length, buildCorrectString(getDigitArray(s, TOTAL_DIGITS)!!, DIVIDER_POSITION, DIVIDER));
        }
    }

    private fun isInputCorrect(
        s: Editable,
        totalSymbols: Int,
        dividerModulo: Int,
        divider: Char
    ): Boolean {
        var isCorrect = s.length <= totalSymbols // check size of entered string
        for (i in 0 until s.length) { // check that every element is right
            isCorrect = if (i > 0 && (i + 1) % dividerModulo == 0) {
                isCorrect and (divider == s[i])
            } else {
                isCorrect and Character.isDigit(s[i])
            }
        }
        return isCorrect
    }

    private fun buildCorrectString(
        digits: CharArray,
        dividerPosition: Int,
        divider: Char
    ): String? {
        val formatted = StringBuilder()
        for (i in digits.indices) {
            if (digits[i].code != 0) {
                formatted.append(digits[i])
                if (i > 0 && i < digits.size - 1 && (i + 1) % dividerPosition == 0) {
                    formatted.append(divider)
                }
            }
        }
        return formatted.toString()
    }

    private fun getDigitArray(s: Editable, size: Int): CharArray? {
        val digits = CharArray(size)
        var index = 0
        var i = 0
        while (i < s.length && index < size) {
            val current = s[i]
            if (Character.isDigit(current)) {
                digits[index] = current
                index++
            }
            i++
        }
        return digits
    }


}