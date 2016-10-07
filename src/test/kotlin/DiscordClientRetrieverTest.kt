import org.thederps.client.ClientCreator
import org.thederps.client.DiscordClientRetriever
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordClientRetrieverTest {
    private lateinit var clientCreator: ClientCreator

    @Before
    fun setUp() {
        clientCreator = mock(ClientCreator::class.java)
    }

    @Test(expected = IllegalArgumentException::class)
    @Throws(Exception::class)
    fun testInit_throwsExceptionWhenArgsEmpty() {
        DiscordClientRetriever(emptyArray(), clientCreator)
    }

    @Test
    @Throws(Exception::class)
    fun testInit_createsClientWhenArgumentsNonNull() {
        DiscordClientRetriever(arrayOf(""), clientCreator)
    }
}