package com.example.janna_spring.service

import com.example.janna_spring.dto.response.UserResponse
import com.example.janna_spring.entity.Role
import com.example.janna_spring.entity.User
import com.example.janna_spring.repository.UserRepository
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserService(
    private val repository: UserRepository
) {

    fun save(user: User): User {
        return repository.save(user)
    }

    fun create(user: User): User {
        if (repository.existsByUsername(user.getUsername())) {
            throw RuntimeException("Пользователь с таким именем уже существует")
        }
        if (repository.existsByEmail(user.email)) {
            throw RuntimeException("Пользователь с таким email уже существует")
        }
        return save(user)
    }

    private fun getByUsername(username: String?): User {
        return repository.findByUsername(username)!!
    }
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username: String? -> getByUsername(username) }
    }

    fun currentUser(): UserResponse {
        val username = SecurityContextHolder.getContext().authentication.name
        val user = getByUsername(username)
        return UserResponse(user.id, user.email, user.username)
    }

}