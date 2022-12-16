import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.basicApi() {

    get("test") {
        println("Incoming message->${call.receive<TextResponse>().message}")
        call.respond(TextResponse("hello!"))
    }
}


fun Route.dbApi() = route("user") {

    val transactionContext by inject<TransactionContext>()

    get("register") {
        transactionContext.transaction {
            println("here")
            println("documents=${usersCollection.countDocuments()}")
            usersCollection.insertOne(User("a", "a", "a", "a"))
        }
        call.respond(HttpStatusCode.OK)
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
