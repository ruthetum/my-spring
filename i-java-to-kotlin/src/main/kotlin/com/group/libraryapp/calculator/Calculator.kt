package com.group.libraryapp.calculator

class Calculator(
    private var _number: Int
) {
    val number: Int
        get() = _number

    fun add(operand: Int) {
        _number += operand
    }

    fun subtract(operand: Int) {
        _number -= operand
    }

    fun multiply(operand: Int) {
        _number *= operand
    }

    fun divide(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다")
        }
        _number /= operand
    }
}