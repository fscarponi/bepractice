import kotlinx.serialization.Serializable

@Serializable
data class TextResponse(val message: String)
@Serializable
data class User(
    val username: String,
    val firstName: String,
    val lastName: String,
    val mail: String
)

@Serializable
data class UserRegistrationRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val mail: String
)



