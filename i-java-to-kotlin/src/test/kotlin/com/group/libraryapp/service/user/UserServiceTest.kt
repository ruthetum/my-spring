package com.group.libraryapp.service.user

import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTest @Autowired constructor(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    @Test
    fun saveUserTest() {
        // given.
        val request = UserCreateRequest("heedong", null)

        // when.
        userService.saveUser(request)

        // then.
        val results = userRepository.findAll()
        assertThat(results).hasSize(1)
        assertThat(results[0].name).isEqualTo("heedong")
        assertThat(results[0].age).isNull()
    }

    @Test
    fun getUsersTest() {
        // given.
        userRepository.saveAll(listOf(
            User("a", 25),
            User("b", null)
        ))

        // when.
        val results = userService.getUsers()

        // then.
        assertThat(results).hasSize(2)
        assertThat(results).extracting("name").containsExactly("a", "b")
        assertThat(results).extracting("age").containsExactly(25, null)
    }

    @Test
    fun updateUserNameTest() {
        // given.
        val user = userRepository.save(User("a", 25))
        val request = UserUpdateRequest(user.id!!, "b")

        // when.
        userService.updateUserName(request)

        // then.
        val results = userRepository.findAll()[0]
        assertThat(results.name).isEqualTo("b")
    }

    @Test
    fun deleteUserTest() {
        // given.
        val user = userRepository.save(User("a", 25))

        // when.
        userService.deleteUser("a")

        // then.
        assertThat(userRepository.findAll()).hasSize(0)
    }

    @AfterEach
    fun clean() {
        userRepository.deleteAll()
    }
}