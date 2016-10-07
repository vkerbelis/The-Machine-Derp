import org.junit.Test

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordClientRetrieverTest {
    @Test(expected = IllegalArgumentException::class)
    @Throws(Exception::class)
    fun testInit_throwsExceptionWhenArgsEmpty() {
        DiscordClientRetriever(emptyArray())
    }
}