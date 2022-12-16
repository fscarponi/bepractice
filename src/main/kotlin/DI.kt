import com.mongodb.reactivestreams.client.MongoClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.kodein.di.*
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine

object DIModules  {

    val environment
        get() = DI.Module("environment") {
            environmentBoolean("PRODUCTION", default = false)
            environmentString("MONGO_URL", default = "192.168.1.219:27017") { it.prependIfMissing("mongodb://") }
            bind<String>("DATABASE_NAME") with singleton {
                if (instanceOrNull<Boolean>("PRODUCTION") == true) "production" else "test"
            }
        }
    @OptIn(ExperimentalSerializationApi::class)
    val serialization
        get() = DI.Module("serialization") {
            bind<Json>() with singleton {
                Json(from = Json.Default) {
                    serializersModule = instance()
                    prettyPrint = instance<Boolean>("PRODUCTION").not()
                    if (instance<Boolean>("PRODUCTION").not())
                        prettyPrintIndent = "  "
                    encodeDefaults = true
                }
            }
        }

    val database get()= DI.Module("database") {
        bind<CoroutineClient>() with provider { instance<MongoClient>().coroutine }
        bind<CoroutineDatabase>() with singleton {
            instance<MongoClient>().getDatabase(instance("DATABASE_NAME")).coroutine
        }

        bind<TransactionContext>() with provider {
            TransactionContext(di)
        }

        //direct access to collections --> warning no transaction and collection name is bind!
        bind<CoroutineCollection<User>>() with singleton {
            instance<CoroutineDatabase>().getCollection("users")
        }
    }
}

