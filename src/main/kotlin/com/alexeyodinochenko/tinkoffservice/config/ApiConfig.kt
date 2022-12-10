package com.alexeyodinochenko.tinkoffservice.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "api")
class ApiConfig(
    val isSandboxModeEnabled: Boolean
)