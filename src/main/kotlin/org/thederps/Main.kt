package org.thederps

import org.thederps.client.DiscordClientCreator
import org.thederps.client.DiscordClientRetriever
import org.thederps.module.ReconnectModule
import org.thederps.tools.CompletableFutureAsyncRunner

/**
 * @author Vidmantas K. on 2016-10-07.
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val clientCreator = DiscordClientCreator()
        val clientRetriever = DiscordClientRetriever(args, clientCreator)
        val authenticator = DiscordAuthenticator()
        val botController = BotController(clientRetriever)

        val isSetUp = botController.setUp(authenticator)
        if (isSetUp) {
            botController.launchModule(ReconnectModule(authenticator, CompletableFutureAsyncRunner()))
        }
    }
}
