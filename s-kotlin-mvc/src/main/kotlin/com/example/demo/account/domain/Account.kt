package com.example.demo.account.domain

import com.example.demo.common.domain.BaseTimeEntity
import com.example.demo.post.domain.Post
import jakarta.persistence.*
import java.util.ArrayList

@Entity
class Account(
    nickname: String,
    email: String,
    password: String
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(length = 100, nullable = false)
    val nickname: String = nickname

    @Column(length = 100, nullable = false)
    val email: String = email

    @Column(nullable = false)
    val password: String = password

    @OneToMany(mappedBy = "account")
    @OrderBy("created_at DESC")
    val posts: MutableList<Post> = ArrayList()

    companion object {
        fun create(nickname: String, email: String, password: String): Account {
            return Account(nickname, email, password)
        }
    }
}