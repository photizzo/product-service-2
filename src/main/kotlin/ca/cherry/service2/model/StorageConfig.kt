package ca.cherry.service2.model

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class StorageConfig {

    @Value("\${storage.path}")
    lateinit var storagePath: String
}