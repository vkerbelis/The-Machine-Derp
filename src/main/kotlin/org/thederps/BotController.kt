package org.thederps

import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import org.thederps.command.Command
import org.thederps.command.DisconnectCommand
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val clientRetriever: ClientRetriever) {
    private val log = LoggerFactory.getLogger(BotController::class.java)
    private lateinit var client : IDiscordClient

    fun launch(authenticator: Authenticator) {
        try {
            client = clientRetriever.getClient()
            authenticator.login(client)
        } catch (cause: DiscordException) {
            log.warn("Could not launch bot", cause)
        }
    }

    fun registerCommand(command: Command) {
        client.dispatcher.registerListener(command)
    }
}