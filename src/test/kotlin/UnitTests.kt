import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UnitTests {

    @Test
    fun getBinaryTreeDepth1() {
        assertEquals(1, getDepth(Samples.lvl1tree))
    }

    @Test
    fun getBinaryTreeDepth2() {
        assertEquals(2, getDepth(Samples.lvl2tree))
    }

    @Test
    fun getBinaryTreeDepth3() {
        assertEquals(4, getDepth(Samples.lvl4Tree))
    }


}
