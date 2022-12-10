package com.alexeyodinochenko.tinkoffservice.controller

import com.alexeyodinochenko.tinkoffservice.dto.FigiesDto
import com.alexeyodinochenko.tinkoffservice.dto.StocksDto
import com.alexeyodinochenko.tinkoffservice.dto.StocksPricesDto
import com.alexeyodinochenko.tinkoffservice.dto.TickersDto
import com.alexeyodinochenko.tinkoffservice.model.Stock
import com.alexeyodinochenko.tinkoffservice.service.`interface`.StockService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StockController(
    private val stockService: StockService
) {
    @GetMapping("/stocks/{ticker}")
    fun getStock(@PathVariable ticker: String): Stock {
        return stockService.getStockByTicker(ticker)
    }

    @PostMapping("/stocks/getStocksByTickers")
    fun getStocksByTickers(@RequestBody tickersDto: TickersDto): StocksDto {
        return stockService.getStocksByTickers(tickersDto)
    }

    @PostMapping("/prices")
    fun getPrices(@RequestBody figiesDto: FigiesDto): StocksPricesDto {
        return stockService.getStocksPrices(figiesDto)
    }
}