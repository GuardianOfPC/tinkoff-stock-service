package com.alexeyodinochenko.tinkoffservice.dto

import com.alexeyodinochenko.tinkoffservice.model.Stock

data class StocksDto(
    val stocks: List<Stock>
)