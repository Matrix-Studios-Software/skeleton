package ltd.matrixstudios.skeleton.plugins

import com.codahale.metrics.Slf4jReporter
import io.ktor.server.application.*
import io.ktor.server.metrics.dropwizard.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.request.*
import org.slf4j.event.Level
import java.util.concurrent.TimeUnit

/**
 * Class created on 3/21/2025

 * @author Max C.
 * @project skeleton
 * @website https://solo.to/redis
 */
fun Application.configureMonitoring() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    install(DropwizardMetrics) {
        Slf4jReporter.forRegistry(registry)
            .outputTo(this@configureMonitoring.log)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .build()
            .start(10, TimeUnit.SECONDS)
    }
}