import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TextResponse(val message: String)

@Serializable
data class UserAuth(
    val _id: String,
    val username: String,
    val mail: String,
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
    val results: List<UserResponse> = emptyList()
)

@Serializable
data class DepthResponse(
    val maxDepth: Int
)

@Serializable
data class Node(
    val value: String,
    val leftChild: Node? = null,
    val rightChild: Node? = null
)


fun getDepth(node: Node, startDeep: Int = 0): Int {
    val currentDepth = startDeep + 1

    val maxLeftDepth = node.leftChild?.let {
        getDepth(it, currentDepth)
    } ?: currentDepth

    val maxRightDepth = node.rightChild?.let {
        getDepth(it, currentDepth)
    } ?: currentDepth

    return maxOf(maxLeftDepth, maxRightDepth)
}


