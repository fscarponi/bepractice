import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TextResponse(val message: String)

@Serializable
data class User(
    val _id: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val mail: String
)

@Serializable
data class UserAuth(
    val _id: String,
    val username: String,
    val hashPassword: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val signupDate: LocalDateTime
)


@Serializable
data class UserRegistrationRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val mail: String
) {
    fun validateData(): String? = when {
        username.length < 8 -> "Username too short!"
        !mail.isAValidMail() -> "invalid email!"
        else -> null
    }
}

@Serializable
data class UserQueryResponse(
    val results: List<User> = emptyList()
)



