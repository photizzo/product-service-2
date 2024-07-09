package ca.cherry.service2.model

import com.fasterxml.jackson.annotation.JsonInclude

class Analytics {
    data class Request (
        val file: String?,
        val product: String?,
    )

    data class Response (
        val file: String? = "",
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val sum: Int? = null,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val error: String? = null,
    )
}