package ca.cherry.service2.controller

import ca.cherry.service2.model.Analytics
import ca.cherry.service2.model.StorageConfig
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

@RestController
class ServiceController {
    @Autowired
    private lateinit var storageConfig: StorageConfig
    private val log: Logger = LoggerFactory.getLogger(ServiceController::class.java)

    @PostMapping("/summary")
    fun calculate(@RequestBody data: Analytics.Request): ResponseEntity<Analytics.Response> {
        val filePath = "${storageConfig.storagePath}/${data.file}"
        val file = File(filePath)
        println("Processing file: $filePath")

        return try {
            ResponseEntity.ok(Analytics.Response(
                file = data.file,
                sum = parseData(file, data.product)
            ))
        } catch (e: Exception) {
            log.error("Exception2 occurred: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Analytics.Response(
                file = data.file,
                error = "Input file not in CSV format."
            ))
        }
    }

    private fun parseData(file: File, product: String?): Int {
        try {
            val reader = BufferedReader(FileReader(file))
            val parser = CSVParser(reader, CSVFormat.DEFAULT.withHeader())

            val headers = parser.headerMap.keys
            val expectedHeaders = setOf("product", "amount")

            if (headers != expectedHeaders) {
                throw IOException("Input file not in CSV format.")
            }

            var sum = 0
            for (record in parser) {
                if (record.get("product") == product) {
                    sum += record.get("amount").toInt()
                }
            }

            parser.close()
            return sum
        } catch (e: IOException) {
            throw IOException("Input file not in CSV format.", e)
        } catch (e: Exception) {
            throw e
        }
    }
}