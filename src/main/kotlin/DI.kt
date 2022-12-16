import com.mongodb.reactivestreams.client.MongoClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

object DIModules  {

    val environment
        get() = module {
            single {
                DBParameters(
                    production = environmentBoolean("PRODUCTION", default = false),
                    mongoUrl = environmentString(
                        "MONGO_URL",
                        default = "mongodb://127.0.0.1:27017"
                    ) { it.prependIfMissing("mongodb://") },
                    databaseName = if (environmentBoolean("PRODUCTION", default = false)) "db" else "testDB"
                )
            }
        }

    @OptIn(ExperimentalSerializationApi::class)
    val serialization
        get() = module {
            single<Json>() {
                val dbParameters: DBParameters by inject()
                Json(from = Json.Default) {
                    prettyPrint = !dbParameters.production
                    if (!dbParameters.production)
                        prettyPrintIndent = "  "
                    encodeDefaults = true
                }
            }
        }

    val database
        get() = module {
            single {
                val dbParameters: DBParameters by inject()
                KMongo.createClient(dbParameters.mongoUrl)
            }
            factory<CoroutineClient> { get<MongoClient>().coroutine }
            factory {
                val dbParameters: DBParameters by inject()
                get<MongoClient>().getDatabase(dbParameters.databaseName).coroutine
            }

            single {
                TransactionContext()
            }

            //direct access to collections --> warning no transaction and collection name is bind!
            single<CoroutineCollection<User>> {
                get<CoroutineDatabase>().getCollection("users")
            }
        }
}


data class DBParameters(
    val production: Boolean,
    val mongoUrl: String,
    val databaseName: String
)

