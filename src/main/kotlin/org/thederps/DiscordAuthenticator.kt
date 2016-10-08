package org.thederps

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticator() : Authenticator {
    private val log: Logger

    init {
        log = LoggerFactory.getLogger(DiscordAuthenticator::class.java)
    }

    @Throws(DiscordException::class)
    override fun login(client: IDiscordClient) {
        client.login()
        client.dispatcher.registerListener(this)
    }

    @EventSubscriber
    fun onReady(event: ReadyEvent) {
        log.info("*** The Bot is ready")
    }
}