package latency_misreporting_example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import java.lang.Thread.sleep

@Controller("/")
class NotacoroutineController {
    @Get(uri="/notacoroutine", produces=["text/plain"])
    fun index2(): String {
        sleep(5000)
        return "http.server.requests representing elapsed time correctly"
    }
}