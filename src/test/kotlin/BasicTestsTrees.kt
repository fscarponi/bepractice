import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
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
        val bodyJson = File(classLoader.getResource("treeMock1.json").file).readText()
        client.post("depth") {
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(1, it.body<DepthReponse>().maxDepth)
        }
    }

    @Test
    fun testDeepTree2() = testApplication {
        val bodyJson = File(classLoader.getResource("treeMock2.json").file).readText()
        client.post("depth") {
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(2, it.body<DepthReponse>().maxDepth)
        }
    }

    @Test
    fun testDeepTree4() = testApplication {
        val bodyJson = File(classLoader.getResource("treeMock4.json").file).readText()
        client.post("depth") {
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(4, it.body<DepthReponse>().maxDepth)
        }
    }

    @Test
    fun testDeepTree4withNulls() = testApplication {
        val bodyJson = File(classLoader.getResource("treeMock4Explicit.json").file).readText()
        client.post("depth") {
            setBody(bodyJson)
        }.let {
            assertEquals(HttpStatusCode.OK, it.status)
            assertEquals(4, it.body<DepthReponse>().maxDepth)
        }
    }

}
