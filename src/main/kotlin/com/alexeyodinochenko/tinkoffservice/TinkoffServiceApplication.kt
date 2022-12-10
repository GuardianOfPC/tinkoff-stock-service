package com.alexeyodinochenko.tinkoffservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TinkoffServiceApplication

fun main(args: Array<String>) {
    runApplication<TinkoffServiceApplication>(*args)
}
