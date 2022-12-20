import com.mongodb.client.model.UpdateOptions
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.conversions.Bson
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.or
import java.time.LocalDateTime
import java.util.*

@Serializable
data class UserRegistrationQuery(
    @SerialName("\$setOnInsert") val user: UserAuth
)

fun Route.basicApi() {
    get("test") {
        println("Incoming message->${call.receive<TextResponse>().message}")
        call.respond(TextResponse("hello!"))
    }
}

fun Route.dbApi() = route("user") {

    val userAuthCollection by inject<CoroutineCollection<UserAuth>>()
    val json by inject<Json>()

    //this method use transaction but does not scale good
    get("register") {
        val userRequest = call.receive<UserRegistrationRequest>().also { request ->
            request.validateData()?.let { errorMessage ->
                call.respond(HttpStatusCode.BadRequest, TextResponse(errorMessage))
                return@get
            }
        }
        val user = UserAuth(
            username = userRequest.username,
            hashPassword = userRequest.password.digestMD5(),
            signupDate = LocalDateTime.now(),
            mail = userRequest.mail,
            firstName = userRequest.firstName,
            lastName = userRequest.lastName,
        )
        val result = userAuthCollection.updateOne(
            filter = or(UserAuth::username eq userRequest.username, UserAuth::mail eq userRequest.mail),
            update = json.encodeToBson(UserRegistrationQuery(user)),
            options = UpdateOptions().upsert(true)
        )

        if (result.matchedCount.toInt() == 0) {
            call.respond(HttpStatusCode.OK, TextResponse("registration success"))
        } else {
            call.respond(HttpStatusCode.BadRequest, TextResponse("User Already Exists!"))
        }
    }


    get("query") {
        val parametersString =
            call.request.queryParameters.entries().map { it.key to it.value.firstOrNull() }.filter { it.second != null }
                .map { it.first to it.second!! }//without it hint will fail to infer the not nullability
                .toList()
        if (parametersString.isEmpty()) {
            call.respond(HttpStatusCode.BadRequest, "Expected arguments")
            return@get
        }
        val filter: Bson = Document().apply {
            parametersString.forEach {
                append(it.first, it.second)
            }
        }
        transactionContext.transaction {
            usersCollection.find(filter).limit(20).toList()
        }.let {
            call.respond(HttpStatusCode.OK, UserQueryResponse(it))
        }

    }

}

@Serializable
data class UserResponse(val username: String, val mail: String, val firstName: String, val lastName: String)


fun Route.servicesApi() {
    post("depth") {
        val node = call.receive<Node>()
        call.respond(HttpStatusCode.OK, DepthResponse(node.getDepth()))
    }

}
