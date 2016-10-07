package org.thederps

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticator(clientRetriever: ClientRetriever) : Authenticator {
    private val log: Logger
    private val client = clientRetriever.getClient()

    init {
        log = LoggerFactory.getLogger(DiscordAuthenticator::class.java)
    }

    override fun login() {
        client.login()
        client.dispatcher.registerListener(this)
    }

    @EventSubscriber
    fun onReady(event: ReadyEvent) {
        log.info("*** The Bot is ready")
    }
}