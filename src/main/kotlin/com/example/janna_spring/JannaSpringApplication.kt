package com.example.janna_spring

import com.example.janna_spring.dto.response.JwtAuthenticationResponse
import com.example.janna_spring.dto.request.LoginRequest
import com.example.janna_spring.dto.request.RegistrationRequest
import com.example.janna_spring.dto.response.UserResponse
import com.example.janna_spring.service.AuthenticationService
import com.example.janna_spring.service.MessageService
import com.example.janna_spring.service.UserService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@SpringBootApplication()
//@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class JannaSpringApplication

fun main(args: Array<String>) {
    runApplication<JannaSpringApplication>(*args)
}

@RestController
class MessageController(val service: MessageService) {
    @GetMapping("/")
    fun index(): List<Message> = service.findMessages()

    @PostMapping("/")
    fun post(@RequestBody message: Message) {
        service.save(message)
    }
}


@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/register")
    fun signUp(@RequestBody request: RegistrationRequest): JwtAuthenticationResponse {
        return authenticationService.signUp(request)
    }

    @PostMapping("/login")
    fun signIn(@RequestBody request: LoginRequest): JwtAuthenticationResponse {
        return authenticationService.signIn(request)
    }
}

@RestController
@RequestMapping("/api/v1/user")
class UserController(
    private val userService: UserService
) {
    @GetMapping("/me")
    fun getSelfProfile(): UserResponse {
        return userService.currentUser()
    }
}