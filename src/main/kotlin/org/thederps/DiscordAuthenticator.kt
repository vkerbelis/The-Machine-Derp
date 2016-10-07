package org.thederps

import org.thederps.client.ClientRetriever

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticator(clientRetriever: ClientRetriever) : Authenticator {
    private val client = clientRetriever.getClient()

    override fun login() {
        client.login()
    }
}