package com.alexeyodinochenko.tinkoffservice.model

data class Stock(
    val ticker: String,
    val figi: String,
    val name: String,
    val type: String,
    val currency: Currency,
    val source: String,
)