package latency_misreporting_example

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.RxHttpClient
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class CoroutineControllerWithContextTest(private val embeddedServer: EmbeddedServer) {
    val mapper = jacksonObjectMapper()

    @Test
    fun totalTimeShouldBeAtLeast5Seconds() {
        val client: RxHttpClient = embeddedServer.applicationContext.createBean(RxHttpClient::class.java, embeddedServer.url)
        assertEquals(HttpStatus.OK, client.toBlocking().exchange("/coroutine", String::class.java).status())

        val requestsAsJson = client.toBlocking().retrieve("/metrics/http.server.requests")
        val totalTime = mapper.readTree(requestsAsJson).get("measurements")[1].get("value")

        assertTrue(totalTime.asDouble() > 5f) {"totalTime is actually $totalTime, and not 5 seconds"}
        client.close()
    }
}