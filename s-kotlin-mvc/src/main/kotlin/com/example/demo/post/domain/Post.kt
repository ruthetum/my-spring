package com.example.demo.post.domain

import com.example.demo.account.domain.Account
import com.example.demo.common.domain.BaseTimeEntity
import jakarta.persistence.*

@Entity
class Post(
    accountId: Long,
    title: String,
    content: String,
): BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(length = 100, nullable = false)
    var title: String = title

    @Column(columnDefinition = "TEXT", nullable = false)
    var content: String = content

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", insertable = false, updatable = false)
    var account: Account? = null

    @Column(name = "account_id")
    private val accountId: Long? = null

    companion object {
        fun create(accountId: Long, title: String, content: String): Post {
            return Post(accountId, title, content)
        }
    }
}