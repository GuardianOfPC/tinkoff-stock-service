package com.alexeyodinochenko.tinkoffservice.controller

import com.alexeyodinochenko.tinkoffservice.dto.FigiesDto
import com.alexeyodinochenko.tinkoffservice.dto.StockPriceDto
import com.alexeyodinochenko.tinkoffservice.dto.TickersDto
import com.alexeyodinochenko.tinkoffservice.model.Stock
import com.alexeyodinochenko.tinkoffservice.service.`interface`.StockService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller

@Controller
class StockControllerGraphQL(
    private val stockService: StockService
) {
    @QueryMapping
    fun getStockByTicker(@Argument ticker: String): Stock {
        return stockService.getStockByTicker(ticker)
    }

    @QueryMapping
    fun getStocksByTickers(@Argument tickers: List<String>): List<Stock> {
        return stockService.getStocksByTickers(TickersDto(tickers)).stocks
    }

    @QueryMapping
    fun getPrices(@Argument figies: List<String>): List<StockPriceDto> {
        return stockService.getStocksPrices(FigiesDto(figies)).prices
    }
}