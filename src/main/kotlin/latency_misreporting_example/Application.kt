package latency_misreporting_example

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("latency_misreporting_example")
		.start()
}

