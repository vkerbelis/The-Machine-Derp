package org.thederps

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import org.thederps.tools.AsyncRunner
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.DiscordDisconnectedEvent
import sx.blah.discord.handle.impl.events.MessageReceivedEvent
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.util.DiscordException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Vidmantas on 2016-10-07.
 */
class DiscordAuthenticator(clientRetriever: ClientRetriever, val asyncRunner: AsyncRunner) : Authenticator {
    private val log: Logger
    private val client = clientRetriever.getClient()
    private val reconnect = AtomicBoolean(true)

    init {
        log = LoggerFactory.getLogger(DiscordAuthenticator::class.java)
    }

    @Throws(DiscordException::class)
    override fun login() {
        client.login()
        client.dispatcher.registerListener(this)
    }

    @EventSubscriber
    fun onReady(event: ReadyEvent) {
        log.info("*** The Bot is ready")
    }

    @EventSubscriber
    fun onDisconnect(event: DiscordDisconnectedEvent) {
        asyncRunner.run({
            if (reconnect.get()) {
                log.info("Reconnecting bot")
                try {
                    login()
                } catch (cause: DiscordException) {
                    log.warn("Failed to reconnect bot", cause)
                }
            }
        })
    }

    @EventSubscriber
    fun onMessage(event: MessageReceivedEvent) {
        log.debug("Received a message")
    }
}