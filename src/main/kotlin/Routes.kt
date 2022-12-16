import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.basicApi() {

    get("test") {
        println("Incoming message->${call.receive<TextResponse>().message}")
        call.respond(TextResponse("hello!"))
    }
}


fun Route.dbApi() = route("user") {



    get("register") {
        databaseTransaction {

        }
        error("not yet implemented")
    }


    get("query") {
        error("not yet implemented")
    }


    post("query") {
        error("not yet implemented")
    }

}


fun Route.servicesApi() {

    get("depth") {
        error("not yet implemented")
    }

}
