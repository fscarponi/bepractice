import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UnitTests {

    @Test
    fun getBinaryTreeDepth1() {
        assertEquals(1, Samples.lvl1tree.getDepth())
    }

    @Test
    fun getBinaryTreeDepth2() {
        assertEquals(2, Samples.lvl2tree.getDepth())
    }

    @Test
    fun getBinaryTreeDepth3() {
        assertEquals(4, Samples.lvl4Tree.getDepth())
    }


}
