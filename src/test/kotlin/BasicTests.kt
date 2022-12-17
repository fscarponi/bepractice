import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.java.KoinJavaComponent.getKoin
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import java.time.LocalDateTime
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {


    @BeforeTest
    fun startDI() {
        startKoin {
            // declare used modules
            modules(listOf(DIModules.environment, DIModules.database, DIModules.serialization))
        }
        val dbParameters = getKoin().get<DBParameters>()
        assertEquals(dbParameters.databaseName, "testDB")
        clearDatabase()
    }

    @AfterTest
    fun stopDI() {
        clearDatabase()
        stopKoin()
    }

    private fun clearDatabase() {
        //remove user created for tests
        runBlocking {
            val client = KMongo.createClient().coroutine
            val database = client.getDatabase("testDB")
            database.getCollection<User>("users").drop()
            database.getCollection<User>("authUserInfo").drop()
        }
        println("zzz..zz.. db cleared! ${LocalDateTime.now()}")
    }

    private fun loadTestsUserOnDB() {
        val client = KMongo.createClient().coroutine
        val database = client.getDatabase("testDB")
        runBlocking {
            database.getCollection<User>("users").insertMany(
                Samples.users
            )
        }
    }


    @Test
    fun dummyTest() {
        //test for junit 5 -> this should never fail!
        assertTrue(true)
    }

    @Test
    fun testRoot() = testApplication {
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        client.get("/test") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(Json.encodeToString(TextResponse("Are you there?")))
        }.let {
            assertEquals(
                "hello!",
                Json.decodeFromString<TextResponse>(it.bodyAsText()).message
            )
        }
    }

    @Test
    fun testRegistrationSuccess() = testApplication {
        //clear db before test
        val goodUser = UserRegistrationRequest(
            "username",
            "password123",
            "Jhon",
            "Doe",
            "jhondoe@gmail.com"
        )
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        client.get("/user/register") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(Json.encodeToString(goodUser))
        }.let {
            assertEquals(
                HttpStatusCode.OK,
                it.status
            )
        }
    }

    @Test
    fun testRegistrationInvalid() = testApplication {
        //clear db before test
        val badUser = UserRegistrationRequest(
            "username2",
            "password123",
            "Jhon",
            "Doe",
            "abc"//invalid email
        )
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        client.get("/user/register") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(Json.encodeToString(badUser))
        }.let {
            assertEquals(
                HttpStatusCode.BadRequest,
                it.status
            )
        }
    }

    @Test
    fun testQuerySuccessEasy() = testApplication {
        //clear db before test
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        loadTestsUserOnDB()

        client.get("/user/query") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            url {
                parameters.append("firstName", "Jhon")
            }
        }.let {
            val results = Json.decodeFromString<UserQueryResponse>(it.bodyAsText()).results
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(2, results.size)
        }
    }

    @Test
    fun testQuerySuccessMedium() = testApplication {
        //clear db before test
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        loadTestsUserOnDB()

        client.get("/user/query") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(Json.encodeToString("todo"))
            url {
                parameters.append("username", "jhondoe")
                parameters.append("firstName", "Jhon")
            }
        }.let {
            val results = Json.decodeFromString<UserQueryResponse>(it.bodyAsText()).results
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(1, results.size)
        }
    }

    @Test
    fun testQueryFail() = testApplication {
        //clear db before test
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        client.get("/user/query") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(Json.encodeToString(TextResponse("bad body")))
        }.let {
            assertEquals(
                HttpStatusCode.BadRequest,
                it.status
            )
        }
    }


}
