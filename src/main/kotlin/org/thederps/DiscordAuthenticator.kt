package org.thederps

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.util.DiscordException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticator() : Authenticator {
    private val log: Logger
    private val terminated = AtomicBoolean(false)

    init {
        log = LoggerFactory.getLogger(DiscordAuthenticator::class.java)
    }

    @Throws(DiscordException::class)
    override fun login(client: IDiscordClient) {
        client.login()
        client.dispatcher.registerListener(this)
    }

    override fun isTerminated() = terminated.get()

    @EventSubscriber
    fun onReady(event: ReadyEvent) {
        log.info("*** The Bot is ready")
    }
}