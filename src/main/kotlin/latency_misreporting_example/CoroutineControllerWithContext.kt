package latency_misreporting_example

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.TaskScheduler
import io.micronaut.scheduling.annotation.ExecuteOn
import io.reactivex.schedulers.Schedulers.io
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import java.lang.Thread.sleep
import java.util.concurrent.ExecutorService
import javax.inject.Named

private val logger = KotlinLogging.logger { }

@Controller("/")
class CoroutineController(@Named(TaskExecutors.IO) val executor: ExecutorService) {
    @Get(uri = "/coroutine", produces = ["text/plain"])
    suspend fun index(): String {
        withContext(executor.asCoroutineDispatcher()) {
            coroutineScope {
                logger.info { "Sleeping in an IO thread" }
                sleep(5000)
                
                // This async call is here to log the thread it uses, i.e. an IO one, which is inherited from the context.
                // If you simply use the ExecuteOn annotation and not set the context explicitly then async will default to a 
                // coroutine default context which is not what we want.
                async { logger.info { "Asyncing in an IO thread" }; 1 }.await()
            }
        }
        return "http.server.requests mis representing elapsed time"
    }
}