import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.thederps.client.ClientCreator
import org.thederps.client.DiscordClientRetriever
import sx.blah.discord.api.IDiscordClient

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

    @Test
    fun testGetClient_returnsSetClient() {
        val token = "token"
        val expectedClient = mock(IDiscordClient::class.java)
        `when`(clientCreator.withToken(token)).thenReturn(expectedClient)
        val clientRetriever = DiscordClientRetriever(arrayOf(token), clientCreator)

        val actualClient = clientRetriever.getClient()

        assertSame("Client mismatch", expectedClient, actualClient)
    }
}