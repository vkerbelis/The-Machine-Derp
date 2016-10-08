package org.thederps

import org.slf4j.LoggerFactory
import org.thederps.client.ClientRetriever
import org.thederps.command.Command
import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.util.DiscordException

/**
 * @author Vidmantas K. on 2016-10-07.
 */
class BotController(val clientRetriever: ClientRetriever) {
    private val log = LoggerFactory.getLogger(BotController::class.java)
    private lateinit var client: IDiscordClient

    fun launch(authenticator: Authenticator): Boolean {
        try {
            client = clientRetriever.getClient()
            authenticator.login(client)
            return true
        } catch (cause: DiscordException) {
            log.warn("Could not launch bot", cause)
        }
        return false
    }

    fun registerCommand(command: Command) {
        client.dispatcher.registerListener(command)
    }
}