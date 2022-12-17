import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.bson.Document
import org.bson.conversions.Bson
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq
import org.litote.kmongo.or
import java.time.LocalDateTime
import java.util.*

fun Route.basicApi() {

    get("test") {
        println("Incoming message->${call.receive<TextResponse>().message}")
        call.respond(TextResponse("hello!"))
    }
}


fun Route.dbApi() = route("user") {

    val transactionContext by inject<TransactionContext>()

    get("register") {
        val userRequest = call.receive<UserRegistrationRequest>().also { request ->
            request.validateData()?.let { errorMessage ->
                call.respond(HttpStatusCode.BadRequest, errorMessage)
                return@get
            }
        }
        transactionContext.transaction {
            //we are asserting that user and authUser collections are synced
            usersCollection.findOne(
                or(User::username eq userRequest.username, User::mail eq userRequest.mail)
            )?.let {
                call.respond(HttpStatusCode.BadRequest, "User Already Exist")
                return@transaction
            }
            val id = UUID.randomUUID().toString()
            userRequest.apply {
                usersCollection.insertOne(User(id, username, firstName, lastName, mail))
                usersAuthCollection.insertOne(UserAuth(id, username, password.digestMD5(), LocalDateTime.now()))
            }
            call.respond(HttpStatusCode.OK, TextResponse("registration success"))
        }

    }


    get("query") {
        val parametersString = call.request.queryParameters.entries()
            .map { it.key to it.value.firstOrNull() }
            .filter { it.second != null }
            .map { it.first to it.second!! }
            .toList()
        println("--> parameters filtered = $parametersString")
        if (parametersString.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, "Expected arguments")
            return@get
        }
        val filter: Bson = Document().apply {
            parametersString.forEach {
                append(it.first, it.second)
            }
        }
        println("filter= $filter")
        transactionContext.transaction {
            usersCollection.find(filter).limit(20).toList()
        }.let {
            call.respond(HttpStatusCode.OK, UserQueryResponse(it))
        }

    }

}


fun Route.servicesApi() {

    get("depth") {
        error("not yet implemented")
    }

}
