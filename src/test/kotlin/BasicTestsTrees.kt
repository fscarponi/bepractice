import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BasicTestsTrees {

    private val classLoader = javaClass.classLoader

    @Test
    fun dummyTest() {
        //test for junit 5 -> this should never fail!
        assertTrue(true)
    }

    @Test
    fun testDeepTree1() = testApplication {
        application {
            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                allowHeader(HttpHeaders.Authorization)
                allowHeader(HttpHeaders.ContentType)
                anyHost() // @TODO: REMOVE IN PROD!!
            }
            //list of route
            routing {
                basicApi()
                dbApi()
                servicesApi()
            }
        }
        val bodyJson = File(classLoader.getResource("treeMock1.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(1, Json.decodeFromString<DepthResponse>(it.bodyAsText()).maxDepth)
        }
    }

    @Test
    fun testDeepTree2() = testApplication {
        application {
            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                allowHeader(HttpHeaders.Authorization)
                allowHeader(HttpHeaders.ContentType)
                anyHost() // @TODO: REMOVE IN PROD!!
            }
            //list of route
            routing {
                basicApi()
                dbApi()
                servicesApi()
            }
        }
        val bodyJson = File(classLoader.getResource("treeMock2.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(2, Json.decodeFromString<DepthResponse>(it.bodyAsText()).maxDepth)
        }
    }

    @Test
    fun testDeepTree4() = testApplication {
        application {
            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                allowHeader(HttpHeaders.Authorization)
                allowHeader(HttpHeaders.ContentType)
                anyHost() // @TODO: REMOVE IN PROD!!
            }
            //list of route
            routing {
                basicApi()
                dbApi()
                servicesApi()
            }
        }
        val bodyJson = File(classLoader.getResource("treeMock4.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(4, Json.decodeFromString<DepthResponse>(it.bodyAsText()).maxDepth)
        }
    }

    @Test
    fun testDeepTree4withNulls() = testApplication {
        application {
            install(ContentNegotiation) {
                json()
            }
            install(CORS) {
                allowHeader(HttpHeaders.Authorization)
                allowHeader(HttpHeaders.ContentType)
                anyHost() // @TODO: REMOVE IN PROD!!
            }
            //list of route
            routing {
                basicApi()
                dbApi()
                servicesApi()
            }
        }
        val bodyJson = File(classLoader.getResource("treeMock4Explicit.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(4, Json.decodeFromString<DepthResponse>(it.bodyAsText()).maxDepth)
        }
    }


    @Test
    fun testDeepComplexTree() = testApplication {

    }

}
