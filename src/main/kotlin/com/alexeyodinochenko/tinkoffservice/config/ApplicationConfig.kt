package com.alexeyodinochenko.tinkoffservice.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import ru.tinkoff.invest.openapi.OpenApi
import ru.tinkoff.invest.openapi.okhttp.OkHttpOpenApi

@Configuration
@EnableConfigurationProperties(ApiConfig::class)
@EnableAsync
class ApplicationConfig(
    private var apiConfig: ApiConfig
) {
    @Bean
    fun api(): OpenApi {
        return OkHttpOpenApi(System.getenv("ssoToken"), apiConfig.isSandboxModeEnabled)
    }
}