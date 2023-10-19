package com.group.libraryapp.calculator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CalculatorTest {

    private lateinit var calculator: Calculator

    @Test
    fun add() {
        // given.

        // when.
        calculator.add(3)

        // then.
        assertThat(calculator.number).isEqualTo(9)
    }

    @Test
    fun subtract() {
        // given.

        // when.
        calculator.subtract(3)

        // then.
        assertThat(calculator.number).isEqualTo(3)
    }

    @Test
    fun multiply() {
        // given.

        // when.
        calculator.multiply(3)

        // then.
        assert(calculator.number == 18)
    }

    @Test
    fun divide() {
            // given.

        // when.
        calculator.divide(3)

        // then.
        assertThat(calculator.number).isEqualTo(2)
    }

    @Test
    fun divideException() {
        // given.

        // when.
        // then.
        assertThrows<IllegalArgumentException> {
            calculator.divide(0)
        }.apply {
            assertThat(message).isEqualTo("0으로 나눌 수 없습니다")
        }
    }

    @BeforeEach
    fun setUp() {
        calculator = Calculator(6)
    }
}