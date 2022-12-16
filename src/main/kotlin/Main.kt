import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import org.slf4j.event.Level

fun main() {
    println("Starting server!")
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    setHttp()
    installRoutes()
    install(CallLogging) {
        level = Level.DEBUG
    }
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}


fun Application.setHttp() {
    install(CORS) {
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        anyHost() // @TODO: REMOVE IN PROD!!
    }
}

fun Application.installRoutes(){
    //list of route
    routing {
        basicApi()
        dbApi()
        servicesApi()
    }
}
