import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
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
            configureSerialization()
            setHttp()
            installRoutes()
        }
        val bodyJson = File(classLoader.getResource("treeMock1.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(1, Json.decodeFromString<DepthReponse>(it.bodyAsText()).maxDepth)
        }
    }

    @Test
    fun testDeepTree2() = testApplication {
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        val bodyJson = File(classLoader.getResource("treeMock2.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(2, Json.decodeFromString<DepthReponse>(it.bodyAsText()).maxDepth)
        }
    }

    @Test
    fun testDeepTree4() = testApplication {
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        val bodyJson = File(classLoader.getResource("treeMock4.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(4, Json.decodeFromString<DepthReponse>(it.bodyAsText()).maxDepth)
        }
    }

    @Test
    fun testDeepTree4withNulls() = testApplication {
        application {
            configureSerialization()
            setHttp()
            installRoutes()
        }
        val bodyJson = File(classLoader.getResource("treeMock4Explicit.json").file).readText()
        client.post("/depth") {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(4, Json.decodeFromString<DepthReponse>(it.bodyAsText()).maxDepth)
        }
    }

}
