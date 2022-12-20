import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TextResponse(val message: String)

@Serializable
data class UserAuth(
    val username: String,
    val mail: String,
    val hashPassword: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val signupDate: LocalDateTime,
    val firstName: String,
    val lastName: String,
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

@Serializable
data class Node2(
    val value: String,
    val children: List<Node2>
)

//this does not work on stack
suspend fun Node2.getChildrenMaxDepth(currentDepth: Int = 0): Int = coroutineScope {
    children.map { async { it.getChildrenMaxDepth(currentDepth + 1) } }.awaitAll().max()
}

//this work on stack
fun Node.getDepth(currentDepth: Int = 0): Int {
    val nextDepth = currentDepth + 1

    val maxLeftDepth = leftChild
        ?.getDepth(nextDepth)
        ?: nextDepth

    val maxRightDepth = rightChild
        ?.getDepth(nextDepth)
        ?: nextDepth

    return maxOf(maxLeftDepth, maxRightDepth)
}


