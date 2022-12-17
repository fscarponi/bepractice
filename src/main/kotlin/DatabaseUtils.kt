import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.commitTransactionAndAwait

class TransactionContext : KoinComponent {

    val client: CoroutineClient by inject()
    private val dbParameters: DBParameters by inject()
    private val db
        get() = client.getDatabase(dbParameters.databaseName)

    //Users
    val usersCollection: CoroutineCollection<User> by lazy { db.getCollection("users") }
    val usersAuthCollection: CoroutineCollection<UserAuth> by lazy { db.getCollection("authUserInfo") }

}

suspend inline fun <R> TransactionContext.transaction(
    closeClient: Boolean = true,
    action: TransactionContext.() -> R
): R = try {
    client.startSession().use {
        it.startTransaction()
        val r = action()
        it.commitTransactionAndAwait()
        return r
    }
} finally {
    if (closeClient)
        withContext(Dispatchers.IO) {
            client.close()
        }
}



