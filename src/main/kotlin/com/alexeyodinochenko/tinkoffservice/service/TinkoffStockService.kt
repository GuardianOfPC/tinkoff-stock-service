package com.alexeyodinochenko.tinkoffservice.service

import com.alexeyodinochenko.tinkoffservice.dto.FigiesDto
import com.alexeyodinochenko.tinkoffservice.dto.StockPriceDto
import com.alexeyodinochenko.tinkoffservice.dto.StocksDto
import com.alexeyodinochenko.tinkoffservice.dto.StocksPricesDto
import com.alexeyodinochenko.tinkoffservice.dto.TickersDto
import com.alexeyodinochenko.tinkoffservice.exception.StockNotFoundException
import com.alexeyodinochenko.tinkoffservice.model.Currency
import com.alexeyodinochenko.tinkoffservice.model.Stock
import com.alexeyodinochenko.tinkoffservice.service.`interface`.StockService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import ru.tinkoff.invest.openapi.OpenApi
import ru.tinkoff.invest.openapi.model.rest.MarketInstrumentList
import ru.tinkoff.invest.openapi.model.rest.Orderbook
import java.util.Optional
import java.util.concurrent.CompletableFuture

@Service
class TinkoffStockService(
    private val openApi: OpenApi
): StockService {

    @Async
    fun getMarketInstrumentTicker(ticker: String): CompletableFuture<MarketInstrumentList> {
        val context = openApi.marketContext

        return context.searchMarketInstrumentsByTicker(ticker)
    }
    override fun getStockByTicker(ticker: String): Stock {
        val completableFuture = getMarketInstrumentTicker(ticker)

        val list = completableFuture.join().instruments

        if (list.isEmpty()) {
            throw StockNotFoundException("Stock $ticker not found.")
        }

        val item = list[0]

        return Stock(
            item.ticker,
            item.figi,
            item.name,
            item.type.value,
            Currency.valueOf(item.currency.value),
            "TINKOFF"
        )
    }

    override fun getStocksByTickers(tickersDto: TickersDto):StocksDto {

        val marketInstruments: MutableList<CompletableFuture<MarketInstrumentList>> = mutableListOf()

        tickersDto.tickers.forEach {
            marketInstruments.add(getMarketInstrumentTicker(it))
        }

        val stocks: List<Stock> = marketInstruments.map {
            it.join()
        }.mapNotNull { mi ->
            mi.instruments[0]
        }
            .map { mi ->
                Stock(
                    mi.ticker,
                    mi.figi,
                    mi.name,
                    mi.type.value,
                    Currency.valueOf(mi.currency.value),
                    "TINKOFF"
                )
            }

        return StocksDto(stocks)
    }

    @Async
    override fun getPrice(figi: String): CompletableFuture<Optional<Orderbook>> {
        return openApi.marketContext.getMarketOrderbook(figi, 0)
    }

    override fun getStocksPrices(figiesDto: FigiesDto): StocksPricesDto {
        val orderBooks: MutableList<CompletableFuture<Optional<Orderbook>>> = mutableListOf()

        figiesDto.figies.forEach {
            orderBooks.add(getPrice(it))
        }

        val prices = orderBooks.map {
            it.join()
        }.map {ob ->
            ob.orElseThrow{StockNotFoundException("Stock not found.")}
        }.map {
            ob ->
            StockPriceDto(
                ob.figi,
                ob.lastPrice.toDouble()
            )
        }

        return StocksPricesDto(prices)
    }
}