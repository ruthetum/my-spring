package com.group.libraryapp.service.book

import com.group.libraryapp.calculator.domain.book.Book
import com.group.libraryapp.calculator.domain.user.User
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanHistoryRepository
import com.group.libraryapp.dto.book.request.BookLoanRequest
import com.group.libraryapp.dto.book.request.BookRequest
import com.group.libraryapp.dto.book.request.BookReturnRequest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BookServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userLoanHistoryRepository: UserLoanHistoryRepository,
    private val bookRepository: BookRepository,
    private val bookService: BookService
) {
    @Test
    fun saveBookTest() {
        // given.
        val request = BookRequest("클린 코드")

        // when.
        bookService.saveBook(request)

        // then.
        val results = bookRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("클린 코드")
    }

    @Test
    fun loanBookTest() {
        // given.
        bookRepository.save(Book("클린 코드"))
        val user = userRepository.save(User("heedong", null))
        val request = BookLoanRequest("heedong", "클린 코드")

        // when.
        bookService.loanBook(request)

        // then.
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("클린 코드")
        assertThat(results[0].user.id).isEqualTo(user.id)
        assertThat(results[0].isReturn).isFalse()
    }

    @Test
    fun loanBookTestException() {
        // given.
        bookRepository.save(Book("클린 코드"))
        val user = userRepository.save(User("heedong", null))
        val request1 = BookLoanRequest("heedong", "클린 코드")
        bookService.loanBook(request1)

        val user2 = userRepository.save(User("heedong2", null))
        val request2 = BookLoanRequest("heedong2", "클린 코드")

        // when.
        // then.
        assertThatThrownBy { bookService.loanBook(request2) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("진작 대출되어 있는 책입니다")
    }


    @Test
    fun returnBookTest() {
        // given.
        bookRepository.save(Book("클린 코드"))
        val user = userRepository.save(User("heedong", null))
        val request1 = BookLoanRequest("heedong", "클린 코드")
        bookService.loanBook(request1)
        val request = BookReturnRequest("heedong", "클린 코드")

        // when.
        bookService.returnBook(request)

        // then.
        val results = userLoanHistoryRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].bookName).isEqualTo("클린 코드")
        assertThat(results[0].isReturn).isTrue()
    }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
        bookRepository.deleteAll()
        userLoanHistoryRepository.deleteAll()
    }
}