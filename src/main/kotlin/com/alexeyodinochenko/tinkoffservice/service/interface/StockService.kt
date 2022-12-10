package com.alexeyodinochenko.tinkoffservice.service.`interface`

import com.alexeyodinochenko.tinkoffservice.dto.FigiesDto
import com.alexeyodinochenko.tinkoffservice.dto.StockPriceDto
import com.alexeyodinochenko.tinkoffservice.dto.StocksDto
import com.alexeyodinochenko.tinkoffservice.dto.StocksPricesDto
import com.alexeyodinochenko.tinkoffservice.dto.TickersDto
import com.alexeyodinochenko.tinkoffservice.model.Stock
import ru.tinkoff.invest.openapi.model.rest.Orderbook
import java.util.*
import java.util.concurrent.CompletableFuture

interface StockService {
    fun getStockByTicker(ticker: String): Stock

    fun getStocksByTickers(tickersDto: TickersDto): StocksDto

    fun getPrice(figi: String): CompletableFuture<Optional<Orderbook>>

    fun getStocksPrices(figiesDto: FigiesDto): StocksPricesDto
}