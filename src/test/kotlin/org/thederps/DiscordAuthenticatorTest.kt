package org.thederps

import org.junit.Test
import org.mockito.Mockito.*
import org.thederps.client.ClientRetriever
import sx.blah.discord.api.IDiscordClient

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticatorTest {
    @Test
    fun testLogin_callsClientLogin() {
        val clientRetriever = mock(ClientRetriever::class.java)
        val client = mock(IDiscordClient::class.java)
        `when`(clientRetriever.getClient()).thenReturn(client)
        val authenticator = DiscordAuthenticator(clientRetriever)

        authenticator.login()

        verify(client).login()
    }
}